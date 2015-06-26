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
    
    public Spettacolo(int filmID, String titolo, String genere, String urlTrailer, int durata, String trama, String urlLocandina) {
        this.titolo = titolo;
        this.genere = genere;
        this.urlTrailer = urlTrailer;
        this.durata = durata;
        this.trama = trama;
        this.urlLocandina = urlLocandina;
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
}
