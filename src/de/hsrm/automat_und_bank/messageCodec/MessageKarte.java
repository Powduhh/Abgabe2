package de.hsrm.automat_und_bank.messageCodec;

public class MessageKarte extends Message{

    

    public MessageKarte(String befehl, int nr) {
        super(befehl, nr);
        this.type = MessageType.KARTE;
    }

    
}
