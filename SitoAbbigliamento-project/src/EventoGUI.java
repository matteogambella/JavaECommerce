import com.thoughtworks.xstream.XStream;
import java.io.*;
import java.net.Socket;

public class EventoGUI implements Serializable {
    public String nomeApplicazione;
    public String ipClient;
    public String data;
    public String ora;
    public String etichetta;
    
    public EventoGUI(String n, String ic, String d, String h, String e) {
        this.nomeApplicazione = n;
        this.ipClient = ic;
        this.data = d;
        this.ora = h;
        this.etichetta = e;
    }
    
    private static String serializzaEventoGUI(EventoGUI evnt) { // (1)
        XStream xs = new XStream();
        xs.useAttributeFor(EventoGUI.class, "nomeApplicazione");
        xs.useAttributeFor(EventoGUI.class, "etichetta");
        String xml = xs.toXML(evnt);
        return xml;
    }
    
    public static boolean inviaEventoGUI(EventoGUI evnt, String ip, int porta) { // (2)
        try ( DataOutputStream dout =
          new DataOutputStream( (new Socket(ip, porta) ).getOutputStream());) {
           dout.writeUTF(serializzaEventoGUI(evnt));
        } catch (Exception e) {
            System.out.println("Errore nell'invio dell'evento GUI serializzato");
            return false;
        }
        return true;
    }
    
    public String toString() {
        return "Nome Applicazione: " + nomeApplicazione + "\n" +
               "IP Client: " + ipClient + "\n" +
               "Data: " + data + "\n" +
               "Ora: " + ora + "\n" +
               "Etichetta: " + etichetta;
    }
    
}
/*
Note:
(1): la funzione serializzaEventoGUI restituisce l'oggetto EventoGUI passato
     come stringa, serializzato tramite xml
(2): la funzione inviaEventoGUI permette di inviare l'EventoGUI all'host
     indicato nei parametri. Il contenuto xml viene inviato tramite un
     DataOutputStream in codifica UTF 8.
*/