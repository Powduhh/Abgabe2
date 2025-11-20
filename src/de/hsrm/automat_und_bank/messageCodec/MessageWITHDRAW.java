package de.hsrm.automat_und_bank.messageCodec;

public class MessageWITHDRAW extends Message{

    public MessageWITHDRAW(String befehl, int nr) {
        super(befehl, nr);
        this.type = MessageType.WITHDRAW;
    }

}
