package de.hsrm.automat_und_bank.messageCodec;

public class MessageCodec {

    public static Message encode(String msg) {
        Message nullMessage = new Message("No Message found");
        Message resultMessage;

        if (msg == null) {
            return nullMessage;
        }
        
        String[] parser = msg.split(" ");
        if (parser.length == 2){
            resultMessage = new Message(parser[0], Integer.parseInt(parser[1]));
        }
        else{
            resultMessage = new Message(parser[0]);
        }

        return resultMessage;
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