package de.hsrm.automat_und_bank.exampleTCP;

import java.io.*;
import java.net.*;

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

        while (true) {

            BufferedReader inFromClient =
                    new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            DataOutputStream outToClient =
                    new DataOutputStream(connectionSocket.getOutputStream());


            String request = inFromClient.readLine();
            System.out.println("Empfangen: " + request);
            String[] requestArray = request.split(" ");

            String response;
            // Simples Testprotokoll
            switch (requestArray[0]) {
                case "KARTE":
                    boolean gefunden = false;
                    for (int i : bekannteKarten) {
                        if(i == Integer.parseInt(requestArray[1])){
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
                case "PIN":
                if(karteGefunden){
                    if(requestArray[1] == null ){
                        response = "NICHT OK: Keine Pin";
                        break;
                    }
                    if(Integer.parseInt(requestArray[1]) == richtigePin){
                        response = "OK: Pin richtig";
                        authentifiziert = true;
                        break;
                    }
                    response = "Nicht OK: Pin falsch";
                    break;
                }
                case "BALANCE":
                    if(authentifiziert){
                        response = "Kontostand: 1234.56 EUR\n";
                        break;
                    }
                    response = "Kontostand: gesperrt, nicht authentifiziert\n";
                    break;
                case "WITHDRAW":
                    if(authentifiziert){
                        response = "OK: 100 EUR abgehoben\n";
                        break;
                    }
                    response = "Nicht authentifiziert";
                    break;
                case "EXIT":
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
