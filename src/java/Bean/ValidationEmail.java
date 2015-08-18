/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author Paolo
 */
public class ValidationEmail {
    
    String serverEmail;
    String hostEmail;
    String indirizzoMittente;
    String indirizzoDestinatario;
    String testo;
    
    public ValidationEmail(){}

    /**
     * Metodo di convenienza, crea già un testo di default con il codice di autenticazione dato
     */
    public ValidationEmail(String indirizzoDestinatario, double codiceValidazione){
        this.indirizzoDestinatario = indirizzoDestinatario;
        
        StringBuilder s = new StringBuilder();
        s.append("Ciao! Abbiamo bisogno che confermi la tua email per usare Cineland; clicca su questo link. Se non riesci a cliccarlo, copialo e incollalo nella barra degli indirizzi");
        s.append(" http://localhost:8084/CineLand/Controller?codiceVal=");
        s.append(codiceValidazione);
        s.append("&email=");
        s.append(indirizzoDestinatario);
        s.append("&op=validazione");
        
        testo = s.toString();
    }
    
    public ValidationEmail(String indirizzoDestinatario,String testo) {
        this.indirizzoDestinatario = indirizzoDestinatario;
        this.testo = testo;
    }
    
    /**
     * Invia l'email con i dati inseriti dal costruttore 
     */
    public void Invia(){
        try { 
            Properties props = System.getProperties();
            
            props.setProperty("mail.tranport.protocol", "smtp");
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("isSSL","true");
            //props.put("mail.smtp.startssl.enable", "true");
            props.put( "mail.debug", "true" );
          
            
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getDefaultInstance(props,auth);
            String msgBody = testo;
            
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("progettoWeb94@gmail.com"));
            msg.addRecipient(Message.RecipientType.TO,new InternetAddress(indirizzoDestinatario));
            msg.setSubject("Email di Conferma Posto; no reply");
            msg.setText(msgBody);
           
            Transport.send(msg);
            
        } catch (MessagingException e) {
            System.out.println(e.toString());
            e.printStackTrace(); // USARE LOG4J!!!
        }
    }
    
    public void InviaReset(){
        try { 
            Properties props = System.getProperties();
            
            props.setProperty("mail.tranport.protocol", "smtp");
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("isSSL","true");
            //props.put("mail.smtp.startssl.enable", "true");
            props.put( "mail.debug", "true" );
          
            
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getDefaultInstance(props,auth);
            String msgBody = testo;
            
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("progettoWeb94@gmail.com"));
            msg.addRecipient(Message.RecipientType.TO,new InternetAddress(indirizzoDestinatario));
            msg.setSubject("Email di Reset Password; no reply");
            msg.setText(msgBody);
           
            Transport.send(msg);
            
        } catch (MessagingException e) {
            System.out.println(e.toString());
            e.printStackTrace(); // USARE LOG4J!!!
        }
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
           String username = "progettoWeb94@gmail.com";
           String password = "passwordMoltoSicura";
           return new PasswordAuthentication(username, password);
        }
    
    }
}