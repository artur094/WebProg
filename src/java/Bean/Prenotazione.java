/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import java.util.Date;

/**
 *
 * @author Utente
 */
public class Prenotazione {
    private int prenotazioneID;
    private Utente utente;
    private int spettacoloID;
    private int IDprezzo;
    private int postoID;
    private Date dataOraOperazione;
    
    public Prenotazione(){}

    public Prenotazione(Utente utente, int spettacoloID, int prezzoID, int postoID) {
        this.utente = utente;
        this.spettacoloID = spettacoloID;
        this.IDprezzo = prezzoID;
        this.postoID = postoID;
        this.dataOraOperazione =new Date();
    }

    public int getPrenotazioneID() {
        return prenotazioneID;
    }

    public void setPrenotazioneID(int prenotazioneID) {
        this.prenotazioneID = prenotazioneID;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public int getSpettacoloID() {
        return spettacoloID;
    }

    public void setSpettacoloID(int spettacoloID) {
        this.spettacoloID = spettacoloID;
    }

    public int getPrezzo() {
        return IDprezzo;
    }

    public void setPrezzo(int prezzoID) {
        this.IDprezzo = prezzoID;
    }

    public int getPostoID() {
        return postoID;
    }

    public void setPostoID(int postoID) {
        this.postoID = postoID;
    }

    public Date getDataOraOperazione() {
        return dataOraOperazione;
    }

    public void setDataOraOperazione(Date dataOraOperazione) {
        this.dataOraOperazione = dataOraOperazione;
    }
    
    
}
