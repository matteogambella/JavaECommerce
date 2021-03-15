
import java.util.List;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class TabellaCarrelloSpesa extends TableView<ArticoloCarrello>{
    
     private final ObservableList<ArticoloCarrello> ol;
     
     public TabellaCarrelloSpesa(){
        TableColumn nomeprodCol=new TableColumn("NOMEPRODOTTO");
        nomeprodCol.setCellValueFactory(new PropertyValueFactory<>("nomeprodotto"));
        TableColumn coloreCol=new TableColumn("COLORE");
        coloreCol.setCellValueFactory(new PropertyValueFactory<>("colore"));
        TableColumn quantitaCol=new TableColumn("QUANTITÀ");
        quantitaCol.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        TableColumn tagliaCol=new TableColumn("TAGLIA");
        tagliaCol.setCellValueFactory(new PropertyValueFactory<>("taglia"));
        TableColumn prezzoCol=new TableColumn("PREZZO");
        prezzoCol.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        ol=FXCollections.observableArrayList();
        setItems(ol);
        getColumns().addAll(nomeprodCol,coloreCol,quantitaCol,tagliaCol,prezzoCol);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setMaxHeight(142);
        setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));");
        tagliaCol.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;");
        nomeprodCol.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;");     
        coloreCol.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;");     
        quantitaCol.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;");     
        prezzoCol.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;");
        Label tabellavuota=new Label("Carrello vuoto");
        setPlaceholder(tabellavuota);
    }
     
    public void visualizzaArticoliCarrello(List<ArticoloCarrello> articoli){
        ol.clear();
        ol.addAll(articoli);
    } 
   
      
}
