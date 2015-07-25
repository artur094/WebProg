/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Database.DBManager;
import Servlet.Controller;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Utente
 */
public class Posto {
    private int IDsala;
    private int IDposto;
    private Integer IDPrenotazione;
    private int riga;
    private int colonna;
    private boolean esiste;
    private boolean pagato;
    private boolean occupato;

    public static List<Posto> getPosti(String descrizioneSala) throws SQLException{
        DBManager tmp = new DBManager(Controller.URL_DB);
        return tmp.getPosti(descrizioneSala);
    }
    
    public Posto(int IDsala,int IDposto, int riga, int colonna, boolean esiste, boolean pagato) {
        this.IDsala = IDsala;
        this.IDposto = IDposto;
        this.riga = riga;
        this.colonna = colonna;
        this.esiste = esiste;
        this.pagato = pagato;
    }
    
    public Posto()
    {}

    public boolean isOccupato() {
        return occupato;
    }

    public void setOccupato(boolean occupato) {
        this.occupato = occupato;
    }
    
    public int getIDposto() {
        return IDposto;
    }

    public void setIDposto(int IDposto) {
        this.IDposto = IDposto;
    }
    
    public boolean isPagato() {
        return pagato;
    }

    public void setPagato(boolean pagato) {
        this.pagato = pagato;
    }

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
