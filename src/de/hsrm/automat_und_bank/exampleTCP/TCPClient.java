package de.hsrm.automat_und_bank.exampleTCP;

import java.io.*;
import java.net.*;

class ATMClient {

    public static void main(String argv[]) throws Exception {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket("localhost", 6789);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        System.out.println("💳 Verbindung zum BankServer hergestellt.");
        System.out.println("Gib Befehle ein (z. B. AUTH, BALANCE, WITHDRAW, EXIT):");

        String sentence;
        String modifiedSentence;

        while (true) {
            System.out.print(">> ");
            sentence = inFromUser.readLine();  // Eingabe vom Nutzer

            outToServer.writeBytes(sentence + '\n');  // an Server senden

            modifiedSentence = inFromServer.readLine();  // Antwort lesen
            System.out.println("FROM SERVER: " + modifiedSentence);

            if (sentence.equalsIgnoreCase("EXIT")) {
                System.out.println("💳 Verbindung geschlossen.");
                break;
            }
        }

        clientSocket.close();
    }
}
