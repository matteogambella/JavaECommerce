
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class ProdottoOrdine {
    
    private final SimpleStringProperty nomeprodotto;
    private final SimpleStringProperty colore;
    private final SimpleIntegerProperty quantita;
    private final SimpleStringProperty taglia;
    private final Button voto;
    
    public ProdottoOrdine(String np,String clr,int qunt,String tgl,String voto){
        this.nomeprodotto=new SimpleStringProperty(np);
        this.colore=new SimpleStringProperty(clr);
        this.quantita=new SimpleIntegerProperty(qunt);
        this.taglia=new SimpleStringProperty(tgl);
        
        if(voto==null)
            this.voto=new Button("VOTO NON DEFINITO");
        else
            this.voto=new Button("VOTO ASSEGNATO:"+voto);
        
        this.voto.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-background-radius: 30;-fx-background-insets: 0;-fx-text-fill: white;");
        this.voto.setOnAction((ActionEvent ev)->{
            SitoAbbigliamentoSportivo.prodottoOrdine=np;
            SitoAbbigliamentoSportivo.coloreProdottoOrdine=clr;
            SitoAbbigliamentoSportivo.tagliaProdottoOrdine=tgl;
            SitoAbbigliamentoSportivo.visualizzaAssegnaVoto();
        });
    }
    
    public String getNomeprodotto(){return nomeprodotto.get();}
    public String getColore(){return colore.get();}
    public int getQuantita(){return quantita.get();}
    public String getTaglia(){return taglia.get();}
    public Button getVoto(){return voto;}
 
}
