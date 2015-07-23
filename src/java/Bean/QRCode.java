/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import java.io.ByteArrayOutputStream;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author Utente
 */
public class QRCode {
    String testo ;

    public QRCode() {}

    public QRCode(String testo) {
        this.testo = testo;
    }
    
    public ByteArrayOutputStream Genera(){
        ByteArrayOutputStream outputStream = net.glxn.qrgen.QRCode.from(testo).to(ImageType.PNG).stream();
        return outputStream;
    }
    
}
