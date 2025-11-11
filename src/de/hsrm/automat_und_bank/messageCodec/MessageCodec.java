package de.hsrm.automat_und_bank.messageCodec;

public class MessageCodec {

    public static String encode(String msg) {
        if (msg == null) {
            return "[ENC][/ENC]";
        }
        return "[ENC]" + msg + "[/ENC]";
    }

    public static String decode(Message msg) {
        String encoded;
        if (msg == null) {
            return null;
        }
        encoded = msg.toString();
        
        return encoded;
    }
}