/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Database.DBManager;
import Servlet.Controller;
import java.sql.SQLException;

/**
 *
 * @author ivanmorandi
 */
public class Film {

    private Integer id_film;
    private String titolo;
    private String genere;
    private String url_trailer;
    private Integer durata;
    private String trama;
    private String url_locandina;
    private String nome_Sala;
    private String regista;
    private String attori;
    private String frase;
    
    public Film(){}
    
    public static Film getFilmfromDB(int id_film) throws SQLException
    {
        Film f = null;
        DBManager dbm = new DBManager(Controller.URL_DB);
        f = dbm.getFilm(id_film);
        
        return f;
    }

    public static Film getFilmfromDB(String titolo) throws SQLException
    {
        Film f = null;
        DBManager dbm = new DBManager(Controller.URL_DB);
        f = dbm.getFilm(titolo);
        
        return f;
    }

    
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

    
    
    public String getNome_Sala() {
        return nome_Sala;
    }

    public void setNome_Sala(String nome_Sala) {
        this.nome_Sala = nome_Sala;
    }
    
    public Integer getId_film() {
        return id_film;
    }

    public void setId_film(Integer id_film) {
        this.id_film = id_film;
    }

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

    public String getUrl_trailer() {
        return url_trailer;
    }

    public void setUrl_trailer(String url_trailer) {
        this.url_trailer = url_trailer;
    }

    public Integer getDurata() {
        return durata;
    }

    public void setDurata(Integer durata) {
        this.durata = durata;
    }

    public String getTrama() {
        return trama;
    }

    public void setTrama(String trama) {
        this.trama = trama;
    }

    public String getUrl_locandina() {
        return url_locandina;
    }

    public void setUrl_locandina(String url_locandina) {
        this.url_locandina = url_locandina;
    }
    
}
