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

            String request = inFromClient.readLine();
            System.out.println("Empfangen: " + request);

            String response;

            // Simples Testprotokoll
            switch (request) {
                case "AUTH":
                    response = "OK: Authentifizierung erfolgreich\n";
                    break;
                case "BALANCE":
                    response = "Kontostand: 1234.56 EUR\n";
                    break;
                case "WITHDRAW":
                    response = "OK: 100 EUR abgehoben\n";
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
