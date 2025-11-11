package de.hsrm.automat_und_bank.exampleTCP;

import java.io.*;
import java.net.*;

public class BankServerStub {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(6789);
        System.out.println("Stub Server läuft...");

        Socket client = server.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        String msg = in.readLine();
        out.println("Echo: " + msg);

        client.close();
        server.close();
    }
}
