package de.hsrm.automat_und_bank.exampleTCP;

import java.io.*;
import java.net.*;

import de.hsrm.automat_und_bank.messageCodec.Message;
import de.hsrm.automat_und_bank.messageCodec.MessageANSWER;
import de.hsrm.automat_und_bank.messageCodec.MessageCodec;
import de.hsrm.automat_und_bank.messageCodec.MessageNULL;
import de.hsrm.automat_und_bank.messageCodec.MessageType;

class BankServer {

    public static void main(String[] argv) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(6789);
        System.out.println("🏦 BankServer gestartet...");
        Socket connectionSocket = welcomeSocket.accept();
        System.out.println("Client verbunden: " + connectionSocket.getInetAddress());

        // Testcase Variablen 
        int[] bekannteKarten = {
            1, 7,17,42
        };
        int richtigePin = 17;
        boolean authentifiziert = false;
        boolean karteGefunden = false;
        double kontostand = 1312.42;
        boolean gefunden = false;

        
        while (true) {

            BufferedReader inFromClient =
                    new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
    
            DataOutputStream outToClient =
                    new DataOutputStream(connectionSocket.getOutputStream());
                    
            Message responseMessage;
            String requestString = inFromClient.readLine();
            Message requestMessage = MessageCodec.decode(requestString);
            System.out.println("Empfangen: " + requestString);
            String response;

            if(requestMessage.getBefehl().equalsIgnoreCase("Karte")){
                if(!gefunden){
                    for (int i : bekannteKarten) {
                            if(i == requestMessage.getNr()){
                                gefunden = true;
                            }
                        }
                }
            }else if (!karteGefunden) {
                responseMessage = requestMessage.answer("nicht ok, beginnen sie mit befehl karte und der kartennummer\n");
                response = MessageCodec.encode(responseMessage);
                outToClient.writeBytes(response);
                continue;
            }else if (!(requestMessage.getBefehl().equalsIgnoreCase("pin")) && !authentifiziert){
                responseMessage = requestMessage.answer("bitte zuerst befehl pin und pinzahlen eingeben\n");
                response = MessageCodec.encode(responseMessage);
                outToClient.writeBytes(response);
                continue;
            }

            // Simples Testprotokoll
            switch (requestMessage.getType()) {
                case MessageType.KARTE:
                    if(gefunden){
                        responseMessage = requestMessage.answer("OK: Karte gefunden, bitte Pin eingeben\n");
                        karteGefunden = true;
                        break;
                        }
                        responseMessage = requestMessage.answer("NICHT OK: Karte nicht gefunden, versuchen sie es nochmal\n");
                        break;
                case MessageType.PIN:
                    if(requestMessage.getNr() == richtigePin){
                        responseMessage = requestMessage.answer("OK: Pin richtig\n");
                        authentifiziert = true;
                        break;
                    }
                    responseMessage = requestMessage.answer("Nicht OK: Pin falsch\n");
                    break;
                case MessageType.BALANCE:
                        responseMessage = requestMessage.answer("Kontostand: " + kontostand + "\n");
                        break;
                case MessageType.WITHDRAW:
                    if((kontostand - requestMessage.getBetrag()) >= 0){
                        kontostand -= requestMessage.getBetrag();
                        responseMessage = requestMessage.answer("OK: "+ requestMessage.getBetrag() + " EUR abgehoben \n");
                        break;
                    }
                        responseMessage = requestMessage.answer("Kontostand nicht ausreichend \n");
                        break;
                case MessageType.EXIT:
                    responseMessage = requestMessage.answer("Verbindung beendet\n");
                    response = MessageCodec.encode(responseMessage);
                    outToClient.writeBytes(response);
                    connectionSocket.close();
                    continue; // wartet auf neuen Client
                default:
                    responseMessage = new MessageNULL("Es wurde keine passende Eingabe gemacht\n");
            }
            response = MessageCodec.encode(responseMessage);
            outToClient.writeBytes(response);
        }
    }
}
