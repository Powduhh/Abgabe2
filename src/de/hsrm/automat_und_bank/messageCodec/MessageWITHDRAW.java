package de.hsrm.automat_und_bank.messageCodec;

public class MessageWITHDRAW extends Message{

    public MessageWITHDRAW(String befehl, double betrag) {
        super(befehl, betrag);
        this.type = MessageType.WITHDRAW;
    }

}
