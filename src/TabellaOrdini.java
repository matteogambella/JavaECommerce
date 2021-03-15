
import java.util.List;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class TabellaOrdini extends TableView<Ordine> {
    
     private final ObservableList<Ordine> ol; 
      
     public TabellaOrdini(){
        TableColumn codiceordineCol=new TableColumn("CODICEORDINE");
        codiceordineCol.setCellValueFactory(new PropertyValueFactory<>("codiceordine"));
        TableColumn dataCol=new TableColumn("DATAINVIOORDINE");
        dataCol.setCellValueFactory(new PropertyValueFactory<>("datainvioordine"));
        TableColumn nomespedCol=new TableColumn("NOMESPEDIZIONE");
        nomespedCol.setCellValueFactory(new PropertyValueFactory<>("nomespedizione"));
        TableColumn indirizzospedCol=new TableColumn("INDIRIZZOSPEDIZIONE");
        indirizzospedCol.setCellValueFactory(new PropertyValueFactory<>("indirizzospedizione"));
        TableColumn capCol=new TableColumn("CAP");
        capCol.setCellValueFactory(new PropertyValueFactory<>("cap"));
        TableColumn buttonCol=new TableColumn("MOSTRAPRODOTTI");
        buttonCol.setCellValueFactory(new PropertyValueFactory<>("mostraprodotti"));
        ol=FXCollections.observableArrayList();
        setItems(ol);
        getColumns().addAll(codiceordineCol,dataCol,nomespedCol,indirizzospedCol,capCol,buttonCol);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setMaxHeight(152);
        setMaxWidth(700);
        setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));");
        codiceordineCol.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;-fx-text-alignment:center;");
        dataCol.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;");
        nomespedCol.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;");
        indirizzospedCol.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;");     
        capCol.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;");     
        buttonCol.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;");     
        Label tabellavuota=new Label("Nessun ordine effettuato");
        setPlaceholder(tabellavuota);
    }
     
    public void visualizzaOrdini(List<Ordine> ordini){
        ol.clear();
        ol.addAll(ordini);
    }        
         
}

