package de.hsrm.automat_und_bank.messageCodec;

public class MessageKARTE extends Message{

    public MessageKARTE(String befehl, int nr) {
        super(befehl, nr);
        this.type = MessageType.KARTE;
    }

    
}
