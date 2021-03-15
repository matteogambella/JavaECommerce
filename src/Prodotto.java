
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

 public class Prodotto { 
      
      private final Button btn;
 
      public Prodotto(String i, String d) {
        
        ImageView image = new ImageView("file:../../myfiles/"+d);
        btn=new Button(i,image);
        btn.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));");
        image.setFitHeight(192.0);
        image.setFitWidth(236.0);
        btn.setOnAction((ActionEvent ev)->{
            SitoAbbigliamentoSportivo.nomeProdotto=i;
            SitoAbbigliamentoSportivo.visualizzaProdotto();});
      }
 
      public Button getBtn(){
          return btn;
      }                               

 }