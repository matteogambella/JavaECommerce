
import com.thoughtworks.xstream.XStream;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ParametriDiConfigurazione implements Serializable {
    
    public String nomeUtente;
    public String password;
    public int numeroProdottiGraficoTorta;
    public int numeroGiorniGraficoTorta;
    public int massimaQuantitaProdottiCarrello;
    public String ipServerLog;
    public int portaServerLog;
    public String ipServerDBMS;
    public int portaServerDBMS;
    
    public ParametriDiConfigurazione(String no, String pw, int npg, int ngg,int mqpc, int nc, String il, int pl, String id, int pd) {
        this.nomeUtente = no;
        this.password = pw;
        this.numeroProdottiGraficoTorta = npg;
        this.numeroGiorniGraficoTorta =ngg;
        this.massimaQuantitaProdottiCarrello= mqpc;
        this.ipServerLog = il;
        this.portaServerLog = pl;
        this.ipServerDBMS = id;
        this.portaServerDBMS = pd;
    }
    
    public String toString() {
        String output = "" +
        "Nomeutente: " + nomeUtente +
        "\nPassword: " + password +
        "\nNumero prodotti più venduti da considerare nel grafico a torta: " +numeroProdottiGraficoTorta+
        "\nNumero giorni da considerare nel grafico a torta: " + numeroGiorniGraficoTorta +
        "\nMassima quantità prodotti nel carrello: " + massimaQuantitaProdottiCarrello+
        "\nIndirizzo IP Server di Log: " + ipServerLog +
        "\nPorta Server di Log: " + portaServerLog +
        "\nIndirizzo IP Server DBMS: " + ipServerDBMS +
        "\nPorta Server DBMS: " + portaServerDBMS;
        return output;
    }
    
    public static boolean valida(String nfXML, String nfXSD) { // (1)
        try {
            DocumentBuilder db = 
                  DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory sf = 
                  SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Document d = db.parse(new File(nfXML));
            Schema s = sf.newSchema(new StreamSource(new File(nfXSD)));
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
    
    public static ParametriDiConfigurazione leggi(String nfXML, String nfXSD) { // (2)
        XStream xs = new XStream();
        try {
            String xml = new String(Files.readAllBytes(Paths.get(nfXML)));
    
            if (valida(nfXML, nfXSD)) {
                ParametriDiConfigurazione conf = (ParametriDiConfigurazione) xs.fromXML(xml);
                return conf;
            } else {
                return null;
            }
        } catch (IOException exc) {
            System.out.println("Errore durante la lettura del file di configurazione xml");
            return null;
        }
        
    }
    
}