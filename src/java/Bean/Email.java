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
public class Email {
    
    String serverEmail;
    String hostEmail;
    String indirizzoMittente;
    String indirizzoDestinatario;
    String testo;
    
    public Email(){}

    public Email(String serverEmail, String hostEmail, String indirizzoDestinatario,String indirizzoMittente , String testo) {
        this.serverEmail = serverEmail;
        this.hostEmail = hostEmail;
        this.indirizzoDestinatario = indirizzoDestinatario;
        this.testo = testo;
        this.indirizzoMittente = indirizzoMittente;
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
            String msgBody = "....hi! I'm made of potatoes";
            
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("progettoWeb94@gmail.com"));
            msg.addRecipient(Message.RecipientType.TO,new InternetAddress("paolo.chiste-2@studenti.unitn.it", "Paolo"));
            msg.setSubject("Email di conferma Posto");
            msg.setText(msgBody);
           
            Transport.send(msg);
            
//            Transport transport = session.getTransport("smtp");
//            transport.connect("smtp.google.com","progettoWeb94@gmail.com","passwordMoltoSicura");
//            transport.sendMessage(msg, msg.getAllRecipients());
//            transport.close();
            
        } catch (MessagingException e) {
            e.printStackTrace(); // USARE LOG4J!!!
        }
        catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
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