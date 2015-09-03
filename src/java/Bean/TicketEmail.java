/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 *
 * @author Utente
 */
public class TicketEmail {
    
    private String nomeUtente;
    private String indirizzoDestinatario;
    private double prezzoPagato;
    private String tipoBiglietto;
    private Posto postoRichiesto;
    private Spettacolo spettacoloRichiesto;
    
    public TicketEmail(){}

    public TicketEmail(String nomeUtente, String indirizzoDestinatario ,double prezzoPagato, String tipoBiglietto, Posto postoRichiesto, Spettacolo spettacoloRichiesto) {
        this.nomeUtente = nomeUtente;
        this.prezzoPagato = prezzoPagato;
        this.tipoBiglietto = tipoBiglietto;
        this.postoRichiesto = postoRichiesto;
        this.spettacoloRichiesto = spettacoloRichiesto;
        this.indirizzoDestinatario = indirizzoDestinatario;
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
            MimeMultipart multi = new MimeMultipart();
            
            BodyPart testo = new MimeBodyPart();
            StringBuilder sb = new StringBuilder();
            sb.append("Nome utente: ");
            sb.append(nomeUtente);
            sb.append("Prezzo: " + prezzoPagato);
            sb.append("Tipo Biglietto: " + tipoBiglietto);
            sb.append("Posto: " + postoRichiesto);
            sb.append("Film: " + spettacoloRichiesto.getTitolo());
            sb.append("Ora: " + spettacoloRichiesto.getGiorno());
            
            testo.setText(sb.toString());
            
            BodyPart qrCode = new MimeBodyPart();
            String ext = "dat";
            File dir = new File("/tmp");
            String name = String.format(ext, "qrTemporaneo");
            File file = new File(dir,name);
            
            QRCode qr = new QRCode(testo.toString());
            try{
                FileOutputStream fs = new FileOutputStream(file);
                ByteArrayOutputStream stream = qr.Genera();
                fs.write(stream.toByteArray());
                fs.close();
            }catch(FileNotFoundException ex){
                System.out.println(ex);
            }
            catch(IOException ex){
                System.out.println(ex);
            }
            
            qrCode.setFileName(file.toString());
            
            
            multi.addBodyPart(testo);
            multi.addBodyPart(qrCode);
            
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("progettoWeb94@gmail.com"));
            msg.addRecipient(Message.RecipientType.TO,new InternetAddress(indirizzoDestinatario));
            msg.setSubject("Biglietto Cineland");
            msg.setContent(multi);
           
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
