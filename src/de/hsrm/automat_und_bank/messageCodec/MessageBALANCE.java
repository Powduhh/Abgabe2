package de.hsrm.automat_und_bank.messageCodec;

public class MessageBALANCE extends Message{

    public MessageBALANCE(String befehl) {
        super(befehl);
        this.type =MessageType.BALANCE;
    }

}
