/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

/**
 *
 * @author Utente
 */
public class Posto {
    private int IDsala;
    private Integer IDPrenotazione;
    private int riga;
    private int colonna;
    private boolean esiste;

    public Posto(int IDsala, int riga, int colonna, boolean esiste) {
        this.IDsala = IDsala;
        this.riga = riga;
        this.colonna = colonna;
        this.esiste = esiste;
    }
    
    public Posto()
    {}

    public int getIDsala() {
        return IDsala;
    }

    public void setIDsala(int IDsala) {
        this.IDsala = IDsala;
    }
    
    public Integer getIDPrenotazione() {
        return IDPrenotazione;
    }

    public void setIDPrenotazione(Integer IDPrenotazione) {
        this.IDPrenotazione = IDPrenotazione;
    }

    public int getRiga() {
        return riga;
    }

    public void setRiga(int riga) {
        this.riga = riga;
    }

    public int getColonna() {
        return colonna;
    }

    public void setColonna(int colonna) {
        this.colonna = colonna;
    }

    public boolean isEsiste() {
        return esiste;
    }

    public void setEsiste(boolean esiste) {
        this.esiste = esiste;
    }
    
    
}
