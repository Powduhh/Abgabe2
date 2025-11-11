package de.hsrm.automat_und_bank.exampleTCP;

import java.io.*;
import java.net.*;

public class ATMClientStub {
    public static void main(String[] args) throws Exception {
        Socket client = new Socket("localhost", 6789);
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        out.println("TEST-Nachricht");
        System.out.println(in.readLine());

        client.close();
    }
}
