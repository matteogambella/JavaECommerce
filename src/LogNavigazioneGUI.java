

import java.io.*;
import java.net.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.w3c.dom.Document;
import org.xml.sax.*;

public class LogNavigazioneGUI {
    
    private int porta;
    private String nomeLogXML;
    private String nomeFileXSD;
    
    public LogNavigazioneGUI(int porta, String lXML, String fXSD) {
        this.porta = porta;
        this.nomeLogXML = lXML;
        this.nomeFileXSD = fXSD;
    }
    
    private boolean validaEventoGUI(String evento) {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(evento));
            
            Document d = db.parse(is);
            Schema s = sf.newSchema(new StreamSource(new File(this.nomeFileXSD)));
            s.newValidator().validate(new DOMSource(d));
            return true;
        } catch (Exception e) {
            if (e instanceof SAXException)
                System.out.println("Errore di validazione: " + e.getMessage());
            else
                System.out.println(e.getMessage());
            return false;
        }
    }
    
    public void avvia() {
        try (ServerSocket ls = new ServerSocket(this.porta, 10);) {
            while (true) {
                try (Socket s = ls.accept();
                     DataInputStream oin = new DataInputStream(s.getInputStream());) {
                    String xmlEvent = (String)oin.readUTF();
                    System.out.println(xmlEvent);
                    if (validaEventoGUI(xmlEvent)) {
                        System.out.println("Valido");
                        salvaNelLog(xmlEvent);
                    } else
                        System.out.println("Invalido");
                } catch(IOException exc) {continue;}
            }
        } catch (IOException exc) {}
    }
    
    private void salvaNelLog(String xml) {
        try (FileOutputStream fout = new FileOutputStream(this.nomeLogXML, true)) {
            fout.write((xml + "\n").getBytes());
        } catch(IOException e) {
            System.out.println("Errore append nel log xml");
        }
    }
    
    public static void main(String[] args) {
        int porta = Integer.parseInt(args[0]);
        String logFile = args[1];
        String grammar = args[2];
        LogNavigazioneGUI log = new LogNavigazioneGUI(porta, logFile, grammar);
        log.avvia();
        System.out.println("Sono attivo");
    }
    
}
