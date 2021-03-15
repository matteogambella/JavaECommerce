
import java.util.List;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class TabellaMisure extends TableView<MisureTaglie>{
     
    private final ObservableList<MisureTaglie> ol; 
       
    public TabellaMisure(String macrocategoria){
        TableColumn tagliaCol=new TableColumn("TAGLIA");
        TableColumn misura1Col;
        TableColumn misura2Col;
        TableColumn misura3Col;
        if(macrocategoria.equals("Bottom")){
          misura1Col=new TableColumn("VITA(cm)");
          misura2Col=new TableColumn("FIANCHI(cm)");
          misura3Col=new TableColumn("COSCIA(cm)");
        }
        else
        {
          misura1Col=new TableColumn("PETTO(cm)");
          misura2Col=new TableColumn("VITA(cm)");
          misura3Col=new TableColumn("FIANCHI(cm)");   
        }
        tagliaCol.setCellValueFactory(new PropertyValueFactory<>("taglia"));
        misura1Col.setCellValueFactory(new PropertyValueFactory<>("misura1"));
        misura2Col.setCellValueFactory(new PropertyValueFactory<>("misura2"));
        misura3Col.setCellValueFactory(new PropertyValueFactory<>("misura3")); 
        ol=FXCollections.observableArrayList();
        setItems(ol);
        getColumns().addAll(tagliaCol,misura1Col,misura2Col,misura3Col);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setMaxHeight(142);
        setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));");
        tagliaCol.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;");
        misura1Col.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;");
        misura2Col.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;");
        misura3Col.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;");     
    }  
    
    public void visualizzaMisure(List<MisureTaglie> misure){
        ol.clear();
        ol.addAll(misure);
    } 
    
}
/*
Note:
(1) La tabella discrimina secondo macrocategoria:
    -Se la macrocategoria è Bottom allora le misure prese in considerazione saranno vita,
     fianchi,coscia.
    -Se la macrocategoria è Top allora le misure prese in considerazione saranno petto,vita,
     fianchi. 
*/