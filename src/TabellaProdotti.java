
import java.util.List;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class TabellaProdotti extends TableView<Prodotto>{
     
     private final ObservableList<Prodotto> ol; 
    
     public TabellaProdotti(){
        TableColumn prodottoCol = new TableColumn("PRODOTTI"); 
        prodottoCol.setCellValueFactory(new PropertyValueFactory<>("btn")); 
        prodottoCol.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 14px;");
        prodottoCol.setMinWidth(390);
        ol=FXCollections.observableArrayList();
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));");
        setItems(ol);
        getColumns().add(prodottoCol); 
        Label etichettaTabellaVuota=new Label("Nessun risultato corrispondente alla ricerca");
        setPlaceholder(etichettaTabellaVuota);
     }  
     
     public void visualizzaListaProdotti(List<Prodotto> prodotti){
        ol.clear();
        ol.addAll(prodotti);
     } 
    
}