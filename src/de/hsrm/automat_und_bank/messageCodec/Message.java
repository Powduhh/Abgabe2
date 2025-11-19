package de.hsrm.automat_und_bank.messageCodec;

public abstract class Message {


    String befehl;
    int nr;
    double betrag;
    MessageType type;

    public Message(String befehl, int nr){
        this.befehl = befehl;
        this.nr = nr;

        if(befehl.equals("BETRAG")){
            betrag = nr;
        }
    }

    public Message(String befehl){
        this.befehl = befehl;
    }

    
    @Override
    public String toString() {

        String resultString = this.befehl;
        String resultNR;
        
        if(nr == 0){
            return resultString;
        }
        
        resultNR = Integer.toString(nr);

        resultString = resultString + " " + resultNR;

        return resultString;
    }

    public boolean equals(Message msg) {
        if (this.befehl.equals(msg.befehl) && (this.nr == msg.nr)){
            return true;
        }
        return false;
    }


    //Getter/Setter

    public String getBefehl() {
        return befehl;
    }
    
    public void setBefehl(String befehl) {
        this.befehl = befehl;
    }
    
    public int getNr() {
        return nr;
    }
    
    public void setNr(int nr) {
        this.nr = nr;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }


    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
    
}
