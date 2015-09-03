/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Database.DBManager;
import Servlet.Controller;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ivanmorandi
 */
public class Spettacoli {
    DBManager dbm;
    public Spettacoli()
    {
        try{
            dbm = DBManager.getDBM();
        }
        catch(SQLException sqlex){
            
        }
    }
    
    public Spettacoli(DBManager dbm)
    {
        super();
        this.dbm = dbm;
    }
    
    /*è possibile modificarlo in modo da resiturire gli spettacoli interamente*/
   /* public List<Date> getSpettacoliDisponibili(Film f,Date d){
        try{
            return dbm.getOrariGiornata(f,d);
        }catch(SQLException ex){
            return null;
        }
    }*/
    
    public String getSpettacoliDisponibili(int id_film, Date d)
    {
        String options="";
        try{
            List<Date> spet = dbm.getOrariGiornata(id_film, d);
            for(int i = 0; i < spet.size(); i++){
                Date dd = spet.get(i);
                SimpleDateFormat dataFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");

                String data = dataFormat.format(dd);

               String ora = data.substring(10).split(":")[0];
               String minuti = data.substring(10).split(":")[1];
               //out.println("<option value=\""+ora+"\">" + ora + ":" + minuti + "</option>");
               options+="<option value=\""+ora+"\">" + ora + ":" + minuti + "</option>";
             }
            return options;
        }
        catch(SQLException ex)
        {
            return "";
        }
    }
    
    public Date getMaxData(Film f){
        try{
            List<Date> giornate = dbm.getGiornate(f);
            return giornate.get(giornate.size()-1);
        }catch(SQLException ex){
            return null;
        }
    }
    
    public List<Spettacolo> getSpettacoli(Date data)
            throws SQLException
    {
        List<Spettacolo> lista = null;
        
        lista = dbm.getSpettacoli(data);
        
        return lista;
    }
}
