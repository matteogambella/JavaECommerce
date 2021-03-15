

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class Ordine {
   
   private final SimpleIntegerProperty codiceordine;
   private final SimpleStringProperty  datainvioordine;
   private final SimpleStringProperty  nomespedizione;
   private final SimpleStringProperty  indirizzospedizione;
   private final SimpleStringProperty  cap;
   private final Button mostraprodotti;
   
   public Ordine(int co,String di,String ns,String is,String cap){
       this.codiceordine=new SimpleIntegerProperty(co);
       this.datainvioordine=new SimpleStringProperty(di);
       this.nomespedizione=new SimpleStringProperty(ns);
       this.indirizzospedizione=new SimpleStringProperty(is);
       this.cap=new SimpleStringProperty(cap);
       mostraprodotti=new Button("MOSTRA PRODOTTI");   
       mostraprodotti.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-background-radius: 30;-fx-background-insets: 0;-fx-text-fill: white;");
       mostraprodotti.setOnAction((ActionEvent ev)->{
           SitoAbbigliamentoSportivo.visualizzaProdottiOrdine(co);});
   }
   
   public int getCodiceordine(){return codiceordine.get();}
   public String getDatainvioordine(){return datainvioordine.get();}
   public String getNomespedizione(){return nomespedizione.get();}
   public String getIndirizzospedizione(){return indirizzospedizione.get();}
   public String getCap(){return cap.get();}
   public Button getMostraprodotti(){return mostraprodotti;}
     
}
