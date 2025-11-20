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
            switch (parser[0].toUpperCase()) {
                case "KARTE":
                    resultMessage = new MessageKARTE(parser[0].toUpperCase(), Integer.parseInt(parser[1]));
                    break;
                case "PIN":
                    resultMessage = new MessagePIN(parser[0].toUpperCase(), Integer.parseInt(parser[1]));
                    break;
                case "WITHDRAW":
                    resultMessage = new MessageWITHDRAW(parser[0].toUpperCase(), Double.parseDouble(parser[1]));
                    break;
                default:
                    resultMessage = new MessageNULL("Es wurde keine passende Eingabe gemacht");
                    break;
            }
        }
        else{
            switch (parser[0].toUpperCase()) {
                case "BALANCE":
                    resultMessage = new MessageBALANCE(parser[0].toUpperCase());
                    break;
                case "EXIT":
                    resultMessage = new MessageEXIT(parser[0].toUpperCase());
                    break;
                default:
                    resultMessage = new MessageNULL("Es wurde keine passende Eingabe gemacht");
                    break;
            }
        }

        return resultMessage;
    }

}