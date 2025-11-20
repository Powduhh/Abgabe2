package de.hsrm.automat_und_bank.exampleTCP;

import java.io.*;
import java.net.*;

import de.hsrm.automat_und_bank.messageCodec.Message;
import de.hsrm.automat_und_bank.messageCodec.MessageCodec;
import de.hsrm.automat_und_bank.messageCodec.MessageType;

class BankServer {

    public static void main(String[] argv) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(6789);
        System.out.println("🏦 BankServer gestartet...");
        Socket connectionSocket = welcomeSocket.accept();
        System.out.println("Client verbunden: " + connectionSocket.getInetAddress());

        int[] bekannteKarten = {
            1, 7,17,42
        };
        int richtigePin = 17;
        boolean authentifiziert = false;
        boolean karteGefunden = false;
        double kontostand = 1312.42;

        
        while (true) {

            BufferedReader inFromClient =
                    new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
    
            DataOutputStream outToClient =
                    new DataOutputStream(connectionSocket.getOutputStream());
                    
            String requestString = inFromClient.readLine();
            Message requestMessage = MessageCodec.decode(requestString);
            System.out.println("Empfangen: " + requestString);
            

            String response;
            // Simples Testprotokoll
            switch (requestMessage.getType()) {
                case MessageType.KARTE:
                    boolean gefunden = false;
                    for (int i : bekannteKarten) {
                        if(i == requestMessage.getNr()){
                            gefunden = true;
                        }
                    }
                    if(gefunden){
                        response = "OK: Karte gefunden, bitte Pin eingeben\n";
                        karteGefunden = true;
                        break;
                        }
                        response = "NICHT OK: Karte nicht gefunden, versuchen sie es nochmal\n";
                        break;
                case MessageType.PIN:
                if(karteGefunden){
                    if(requestMessage.getNr() == richtigePin){
                        response = "OK: Pin richtig\n";
                        authentifiziert = true;
                        break;
                    }
                    response = "Nicht OK: Pin falsch\n";
                    break;
                }
                response = "Nicht OK: Keine Karte\n";
                break;
                case MessageType.BALANCE:
                    if(authentifiziert){
                        response = "Kontostand: " + kontostand + "\n";
                        break;
                    }
                    response = "Kontostand: gesperrt, nicht authentifiziert\n";
                    break;
                case MessageType.WITHDRAW:
                    if(authentifiziert){
                        response = "OK: 100 EUR abgehoben\n";
                        break;
                    }
                    response = "Nicht authentifiziert";
                    break;
                case MessageType.EXIT:
                    response = "Verbindung beendet\n";
                    outToClient.writeBytes(response);
                    connectionSocket.close();
                    continue; // wartet auf neuen Client
                default:
                    response = "FEHLER: Unbekannte Nachricht\n";
            }

            outToClient.writeBytes(response);
        }
    }
}
