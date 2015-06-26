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
public class Utente {
    private int userID;
    private String email;
    private String password;
    private double credito;
    private String ruolo;
    
    public Utente(int userID, String email, String password, double credito) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.credito = credito;
        this.ruolo = ""; //inserire codice di ruolo per l'utente normale
    }

    public Utente(int userID, String email, String password, double credito, String ruoloID) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.credito = credito;
        this.ruolo = ruoloID;
    }
    
    public Utente(){     
    
    }
        
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getCredito() {
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
}
