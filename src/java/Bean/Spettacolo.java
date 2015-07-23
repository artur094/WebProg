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
public class Spettacolo {
    private String titolo;
    private String genere;
    private String urlTrailer;
    private int durata;
    private String trama;
    private String urlLocandina;
    private Date ora;
    private String sala;
    private String regista;
    private String attori;
    private String frase;
    //i campi qui sotto forse vanno cancellati, attenzione perchè vengono usati nel dbManager
    private int IDfilm;
    private int IDsala;
    private int IDspettacolo;

    public String getRegista() {
        return regista;
    }

    public void setRegista(String regista) {
        this.regista = regista;
    }

    public String getAttori() {
        return attori;
    }

    public void setAttori(String attori) {
        this.attori = attori;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }
    
    public int getIDsala() {
        return IDsala;
    }

    public void setIDsala(int IDsala) {
        this.IDsala = IDsala;
    }
    

    public int getIDfilm() {
        return IDfilm;
    }

    public void setIDfilm(int IDfilm) {
        this.IDfilm = IDfilm;
    }
    
    public Date getOra() {
        return ora;
    }

    public void setOra(Date ora) {
        this.ora = ora;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }
    
    public Spettacolo(int filmID, String titolo, String genere, String urlTrailer, int durata, String trama, String urlLocandina,String regista,String attori, String frase) {
        this.titolo = titolo;
        this.genere = genere;
        this.urlTrailer = urlTrailer;
        this.durata = durata;
        this.trama = trama;
        this.urlLocandina = urlLocandina;
        this.regista = regista;
        this.attori = attori;
        this.frase = frase;
    }
    
    public Spettacolo(){}
    
    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getUrlTrailer() {
        return urlTrailer;
    }

    public void setUrlTrailer(String urlTrailer) {
        this.urlTrailer = urlTrailer;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public String getTrama() {
        return trama;
    }

    public void setTrama(String trama) {
        this.trama = trama;
    }

    public String getUrlLocandina() {
        return urlLocandina;
    }

    public void setUrlLocandina(String urlLocandina) {
        this.urlLocandina = urlLocandina;
    }

    /**
     * @return the IDspettacolo
     */
    public int getIDspettacolo() {
        return IDspettacolo;
    }

    /**
     * @param IDspettacolo the IDspettacolo to set
     */
    public void setIDspettacolo(int IDspettacolo) {
        this.IDspettacolo = IDspettacolo;
    }
}
