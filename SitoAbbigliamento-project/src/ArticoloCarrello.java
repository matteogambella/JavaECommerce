
import javafx.beans.property.*;


public class ArticoloCarrello {
    
   private final SimpleStringProperty nomeprodotto;
   private final SimpleStringProperty colore;
   private final SimpleIntegerProperty quantita;
   private final SimpleStringProperty taglia;
   private final SimpleDoubleProperty prezzo;
   
   public ArticoloCarrello(String np,String clr,int qunt,String tgl,Double prz){
       this.nomeprodotto=new SimpleStringProperty(np);
       this.colore=new SimpleStringProperty(clr);
       this.quantita=new SimpleIntegerProperty(qunt);
       this.taglia=new SimpleStringProperty(tgl);
       this.prezzo=new SimpleDoubleProperty(prz);
   }
   
   public String    getNomeprodotto(){return nomeprodotto.get();}
   public String    getColore(){return colore.get();}
   public int       getQuantita(){return quantita.get();}
   public String    getTaglia(){return taglia.get();}
   public Double    getPrezzo(){return prezzo.get();}  
   
}
