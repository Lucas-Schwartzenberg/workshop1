/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

/**
 *
 * @author Gebruiker
 */
public class Bestelling {
    private int bestellingID;
    private int klantID;

    public int getBestellingID() {
        return bestellingID;
    }

    public void setBestellingID(int bestellingID) {
        this.bestellingID = bestellingID;
    }

    public void setKlantID(int klantID) {
        this.klantID = klantID;
    }

    public int getKlantID() {
        return klantID;
    }

    public Bestelling(){}
    
    public Bestelling(BestellingBuilder builder){
        this.bestellingID = builder.bestellingID;
        this.klantID = builder.klantID;
    }

    public static class BestellingBuilder{
        private int bestellingID;
        private int klantID;
        
        public BestellingBuilder(){}
        
        public BestellingBuilder bestellingID(int bestellingID){
            this.bestellingID = bestellingID;
            return this;
        }
        
        public BestellingBuilder klantID(int klnat){
            this.klantID = klantID;
            return this;
        }
        public Bestelling build(){
            return new Bestelling(this);
        }
    }
}
