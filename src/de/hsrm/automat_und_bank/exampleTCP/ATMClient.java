package de.hsrm.automat_und_bank.exampleTCP;

import java.io.*;
import java.net.*;

import de.hsrm.automat_und_bank.messageCodec.Message;
import de.hsrm.automat_und_bank.messageCodec.MessageCodec;
import de.hsrm.automat_und_bank.messageCodec.MessageEXIT;

class ATMClient {

    public static void main(String argv[]) throws Exception {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket("localhost", 6789);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        System.out.println("💳 Verbindung zum BankServer hergestellt.");
        System.out.println("Gib Befehle ein (z. B. KARTE NR, PIN NR, BALANCE, WITHDRAW, EXIT):");

        String eingabe;
        String modifiedSentence;

        while (true) {
            System.out.print(">> ");
            eingabe = inFromUser.readLine();  // Eingabe vom Nutzer

            outToServer.writeBytes(eingabe + '\n');  // an Server senden

            modifiedSentence = inFromServer.readLine();  // Antwort lesen
            System.out.println("FROM SERVER: " + modifiedSentence);

            if (eingabe.equals(new MessageEXIT("EXIT"))) {
                System.out.println("💳 Verbindung geschlossen.");
                break;
            }
        }

        clientSocket.close();
    }
}
