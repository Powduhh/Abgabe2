package de.hsrm.automat_und_bank.exampleTCP;

import java.io.*;
import java.net.*;

class BankServer {

    public static void main(String[] argv) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(6789);
        System.out.println("🏦 BankServer gestartet...");

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("Client verbunden: " + connectionSocket.getInetAddress());

            BufferedReader inFromClient =
                    new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            DataOutputStream outToClient =
                    new DataOutputStream(connectionSocket.getOutputStream());


            int[] bekannteKarten = {
                1, 7,17,42
            };
            String request = inFromClient.readLine();
            System.out.println("Empfangen: " + request);
            String[] requestArray = request.split(" ");

            String response;
            boolean authentifiziert = false;
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
                        response = "OK: Authentifizierung erfolgreich\n";
                        authentifiziert = true;
                        break;
                        }
                        response = "NICHT OK: Authentifizierung nicht erfolgreich, versuchen sie es nochmal\n";
                        break;
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
