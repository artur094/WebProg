/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

/**
 *
 * @author ivanmorandi
 */
public class Prezzo {
    private int id_prezzo;
    private double prezzo;
    private String tipo;
    
    public Prezzo(){}

    public int getId_prezzo() {
        return id_prezzo;
    }

    public void setId_prezzo(int id_prezzo) {
        this.id_prezzo = id_prezzo;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
     
}
