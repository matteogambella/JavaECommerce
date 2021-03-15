import java.util.*;
import java.io.*;

public class CacheTabellaCarrelloSpesa{

    private final String nomeFileCache;
    
    public CacheTabellaCarrelloSpesa(String fn) {
        this.nomeFileCache = fn;
    }
    
    public boolean scrivi(List<ArticoloCarrello> list) { // (1)
        boolean success = false;
        try (FileOutputStream fout = new FileOutputStream(this.nomeFileCache);
             ObjectOutputStream oout = new ObjectOutputStream(fout);) {
            Iterator<ArticoloCarrello> it = list.iterator();
            oout.writeObject(list.size()); // (2)
            while(it.hasNext()) { // (3)
                ArticoloCarrello ac = it.next();
                String[] serialAc = new String[5];
                serialAc[0] = ac.getNomeprodotto();
                serialAc[1] = ac.getColore();
                serialAc[2] = Integer.toString(ac.getQuantita());
                serialAc[3] = ac.getTaglia();
                serialAc[4] = Double.toString(ac.getPrezzo());
                oout.writeObject(serialAc);
            }
            success = true;   
        } catch (IOException exc) {
            System.out.println("Errore scrittura cache tabella carrello spesa");
        }
        return success;
    }
    
    public List<ArticoloCarrello> leggi() { // (4)
        List<ArticoloCarrello> carrello = new ArrayList<>();
        try (FileInputStream fin = new FileInputStream(this.nomeFileCache);
             ObjectInputStream oin = new ObjectInputStream(fin);) {
            int num = (int)oin.readObject();
            for(int i=0; i<num; ++i) { 
              String[] serialAc = (String[]) oin.readObject();
              int quantita=Integer.parseInt(serialAc[2]) ;
              double prezzo=Double.parseDouble(serialAc[4]);
              carrello.add(new ArticoloCarrello(serialAc[0], serialAc[1],quantita,serialAc[3],prezzo));
              SitoAbbigliamentoSportivo.prezzoTotaleArticoli+=prezzo;
              SitoAbbigliamentoSportivo.quantitaTotaleArticoli+=quantita;
            }
        } catch (ClassNotFoundException exc) {
            System.out.println("Contenuto cache tabella carrello spesa non valido");
        } catch (IOException exc) {
            System.out.println("Errore lettura cache tabella carrello spesa");
        }
        return carrello;
    }
    
}

