/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import java.util.*;
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
            props.put(serverEmail, hostEmail); // al posto del nome in rosso va il mio server o smarthost provider

            props.put("mail.debug", "true");    //cancellare questa riga quando tutto funziona bene (attiva mod. debug)
            Session session = Session.getDefaultInstance(props);
            Message message = new MimeMessage(session);
            InternetAddress from = new InternetAddress(indirizzoMittente);
            InternetAddress to[] = InternetAddress.parse(indirizzoDestinatario);

            message.setFrom(from);
            //message.setRecipients(Message.RecipientType.TO, to);
            message.setSubject("Test messaggio inviato da Java.");
            message.setSentDate(new Date());
            message.setText("questo\ne' il messaggio\ngino\n");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // USARE LOG4J!!!
        }
    }
}