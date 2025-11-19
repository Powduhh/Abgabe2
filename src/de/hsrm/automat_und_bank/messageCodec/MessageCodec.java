package de.hsrm.automat_und_bank.messageCodec;

public class MessageCodec {

    public static String encode(Message msg) {
        String encoded;
        if (msg == null) {
            return null;
        }
        encoded = msg.toString();
        
        return encoded.toUpperCase();
    }

    public static Message decode(String msg) {
        Message nullMessage = new MessageNULL("No Message found");
        Message resultMessage;

        if (msg == null) {
            return nullMessage;
        }
        
        String[] parser = msg.split(" ");
        if (parser.length == 2){
            resultMessage = new Message(parser[0].toUpperCase(), Integer.parseInt(parser[1]));
        }
        else{
            resultMessage = new Message(parser[0]);
        }

        return resultMessage;
    }

}