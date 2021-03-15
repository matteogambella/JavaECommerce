
import java.util.List;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class TabellaProdottiOrdine extends TableView<ProdottoOrdine>{
    
     private final ObservableList<ProdottoOrdine> ol; 
      
     public TabellaProdottiOrdine(){
        TableColumn nomeprodCol=new TableColumn("NOMEPRODOTTO");
        nomeprodCol.setCellValueFactory(new PropertyValueFactory<>("nomeprodotto"));
        TableColumn coloreCol=new TableColumn("COLORE");
        coloreCol.setCellValueFactory(new PropertyValueFactory<>("colore"));
        TableColumn quantitaCol=new TableColumn("QUANTITÀ");
        quantitaCol.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        TableColumn tagliaCol=new TableColumn("TAGLIA");
        tagliaCol.setCellValueFactory(new PropertyValueFactory<>("taglia"));
        TableColumn votoCol=new TableColumn("VOTO");
        votoCol.setCellValueFactory(new PropertyValueFactory<>("voto"));
        ol=FXCollections.observableArrayList();
        setItems(ol);
        getColumns().addAll(nomeprodCol,coloreCol,quantitaCol,tagliaCol,votoCol);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setMaxHeight(156);
        setMaxWidth(700);
        setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));");
        nomeprodCol.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;");
        coloreCol.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;");
        quantitaCol.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;");
        tagliaCol.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;");     
        votoCol.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;");      
     }  
     
     public void visualizzaListaProdottiOrdine(List<ProdottoOrdine> prodottiordine){
        ol.clear();
        ol.addAll(prodottiordine);
     }        
      
}
