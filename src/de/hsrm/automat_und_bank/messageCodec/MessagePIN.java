package de.hsrm.automat_und_bank.messageCodec;

public class MessagePIN extends Message{

    public MessagePIN(String befehl, int nr) {
        super(befehl, nr);
        this.type = MessageType.PIN;
    }

}
