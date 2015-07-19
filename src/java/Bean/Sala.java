/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Database.DBManager;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ivanmorandi
 */

/*

    0 -> ESISTE e LIBERO
    1 -> PRENOTATO NON PAGATO
    2 -> PRENOTATO e PAGATO
    3 -> NON ESISTE
*/
public class Sala {
    private int id_sala;
    private int id_spettacolo;
    private int max_righe;
    private int max_colonne;
    private String[][] mappa;
    private DBManager dbm;
    
    public Sala(DBManager dbm, int id_spettacolo)
    {
        this.dbm = dbm;
        this.id_spettacolo = id_spettacolo;
        try{
            refreshMappa();
        }
        catch(SQLException sqlex){
            
        }
    }
    
    public void refreshMappa() throws SQLException
    {
        dbm.cancellaPrenotazioniVecchieNonPagate();
        List<Posto> posti_prenotati = dbm.getPostiSala(id_spettacolo);
        List<Posto> posti = dbm.getPosti(id_spettacolo);
        
        mappa = creaMappa(posti);
        
        for (int i = 0; i < posti.size(); i++) {
            int x = posti.get(i).getRiga();
            int y = posti.get(i).getColonna();
            
            mappa[x][y] = "0";
            
            if(!posti.get(i).isEsiste())
                mappa[x][y] = "3";
        }
        
        for (int i = 0; i < posti_prenotati.size(); i++) {
            int x = posti_prenotati.get(i).getRiga();
            int y = posti_prenotati.get(i).getColonna();
            
            if(x<max_righe && y<max_colonne)
            
                mappa[x][y] = "2";
            
            if(!posti_prenotati.get(i).isPagato())
                mappa[x][y] = "1";
        }
    }
    
    private String[][] creaMappa(List<Posto> posti )
    {
        max_righe = 0;
        max_colonne = 0;
        

        
        for (int i = 0; i < posti.size(); i++) {
            if(posti.get(i).getColonna() > max_colonne)
                max_colonne = posti.get(i).getColonna();
            if(posti.get(i).getRiga() > max_righe)
                max_righe = posti.get(i).getRiga();
        }
        
        max_righe++;
        max_colonne++;
        return new String[max_righe][max_colonne];
    }
    
    public String[][] getMappa()
    {
        return mappa;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < max_righe; i++) {
            for (int j = 0; j < max_colonne; j++) {
                s+=mappa[i][j];
                
                if(j<max_colonne-1)
                    s+=",";
            }
            s+="\n";
        }
        return s;
    } 
    
    public int getId_sala() {
        return id_sala;
    }

    public void setId_sala(int id_sala) {
        this.id_sala = id_sala;
    }

    public int getMax_righe() {
        return max_righe;
    }

    public void setMax_righe(int max_righe) {
        this.max_righe = max_righe;
    }

    public int getMax_colonne() {
        return max_colonne;
    }

    public void setMax_colonne(int max_colonne) {
        this.max_colonne = max_colonne;
    }
}
