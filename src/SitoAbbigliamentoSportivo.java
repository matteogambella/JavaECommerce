
import java.net.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javafx.application.Application;
import javafx.collections.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.*;

public class SitoAbbigliamentoSportivo extends Application {
    
    private static final String AVVIO="AVVIO";
    private static final String TERMINE="TERMINE";
    private static final String CERCA="CERCA";
    private static final String SELEZIONEPRODOTTO="SELEZIONE PRODOTTO";
    private static final String AGGIUNGIALCARRELLO="AGGIUNGI AL CARRELLO";
    private static final String CARRELLOSPESA="CARRELLO SPESA";
    private static final String RIMUOVIARTICOLO="RIMUOVI ARTICOLO";
    private static final String INVIAORDINE="INVIA ORDINE";
    private static final String ORDINI="ORDINI";
    private static final String MOSTRAPRODOTTIORDINE="MOSTRA PRODOTTI ORDINE";
    private static final String ASSEGNAVOTOPRODOTTO="ASSEGNA VOTO PRODOTTO";
    private static final String REGISTRAVOTO="REGISTRA VOTO";
    private static final String FILE_CONFIGURAZIONE_XML = "parametridiconfigurazione.xml";
    private static final String FILE_CONFIGURAZIONE_XSD = "parametridiconfigurazione.xsd";
    private static final String FILE_CACHE_CARRELLO_SPESA = "cache_carrello_spesa.bin";
    
    private CacheTabellaCarrelloSpesa cacheCarrelloSpesa;
    public static  boolean disabilitatoInvioEventoGUI;
    public static String nomeScenario;
    public static ParametriDiConfigurazione parametriConfigurazione;
    
    private static Group root;
    private static Group boxMaster=null;
    
    public static TextField genderTextField=new TextField();
    public static TextField categoryTextField=new TextField();
    public static TextField brandTextField=new TextField();
    
    private static TabellaProdotti tabellaProdotti;
    private static TabellaMisure   tabellaMisure;
    private static Double prezzo=0.0;
    public static ToggleGroup group1=new ToggleGroup();
    public static ToggleGroup group2=new ToggleGroup();
    public static TextField   quantityTextField=new TextField("1");
    public static String nomeProdotto;
    public static String sesso;
    public static String categoria;
    public static String marca;
    
    private static VBox  boxTabellaOrdini;
    private static VBox  boxVoti;
    private final static Button bottoneRegistraVoto=new Button("REGISTRA VOTO");
    public final static Label labelAssegnaVoto=new Label();
    public static final ToggleGroup group = new ToggleGroup();
    private static TabellaProdottiOrdine tabellaProdottiOrdine=null;
    private static Label labelProdottiOrdine;
    public static int codiceOrdine;
    public static String prodottoOrdine;
    public static String coloreProdottoOrdine;
    public static String tagliaProdottoOrdine;

    public static List<ArticoloCarrello> listaArticoliCarrello=new ArrayList<>();
    public static final Label messaggioOrdine=new Label();
    public static Label messaggioAggiungiCarrello=new Label();
    public static Label messaggioRimuoviCarrello=new Label();
    public static double prezzoTotaleArticoli=0.0;
    public static int    quantitaTotaleArticoli=0;
    private static int    quantitaNelCarrello=0;
    public static TextField nomeArticoloDaRimuovere=new TextField();
    public static TextField coloreArticoloDaRimuovere=new TextField();
    public static TextField tagliaArticoloDaRimuovere=new TextField();
    public static TextField nomeSped=new TextField();
    public static TextField indirizzoSped=new TextField();
    public static TextField cap=new TextField();
    public static TextField cittaSped=new TextField();
    private static TabellaCarrelloSpesa tabellaCarrelloSpesa=null;
    private static Label totale=null;
      
    public void start(Stage stage) {
        parametriConfigurazione=ParametriDiConfigurazione.leggi(FILE_CONFIGURAZIONE_XML,FILE_CONFIGURAZIONE_XSD);
        System.out.println(parametriConfigurazione.toString());
        cacheCarrelloSpesa=new CacheTabellaCarrelloSpesa(FILE_CACHE_CARRELLO_SPESA);
        nomeScenario="vistapaginainiziale";
        generaEventoGUI(AVVIO);
        stage.setTitle("Sito abbigliamento sportivo");
        GridPane grid1 = new GridPane();
        grid1.setPrefSize(300, 100);
        grid1.setLayoutX(80);
        grid1.setLayoutY(115);
        grid1.setStyle(" -fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 14px;");
        GridPane grid2 = new GridPane();
        grid2.setPrefSize(1200,60);
        grid2.setLayoutX(80);
        grid2.setLayoutY(20);
        grid2.setStyle(" -fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 14px;");
        visualizzaCampoRicerca(grid1);
        visualizzaBarraNavigazione(grid2);
        root=new Group(grid1,grid2);
        Scene scene = new Scene(root, 300, 275,Color.CHOCOLATE); 
        stage.setScene(scene); 
        stage.show();
        stage.setOnCloseRequest((WindowEvent ev)->{
            chiudiApplicazione();
        });
        Group boxpadre=new Group();
        root.getChildren().add(boxpadre);  
        listaArticoliCarrello=cacheCarrelloSpesa.leggi();
        ControlloreCacheScenario.caricaStato();
    }
    
    private static void generaEventoGUI(String etichetta) { // (2)
        String time = new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        try {
            EventoGUI.inviaEventoGUI(new EventoGUI("E-Commerce",
                InetAddress.getLocalHost().getHostAddress(),date,time,etichetta),
                parametriConfigurazione.ipServerLog, parametriConfigurazione.portaServerLog);
        } catch(UnknownHostException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void visualizzaCampoRicerca(GridPane grid){
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Text sceneTitle = new Text("Menù di ricerca del prodotto");
        grid.add(sceneTitle, 0, 0, 2, 1);
        Label gender = new Label("Sesso:");
        grid.add(gender, 0, 1);
        grid.add(genderTextField, 1, 1);
        Label category = new Label("Categoria:");
        grid.add(category,0,2);
        grid.add(categoryTextField,1,2);
        Label brand = new Label("Marca:");
        grid.add(brand,0,3);
        grid.add(brandTextField,1,3);
        Button btn = new Button("Cerca"); 
        btn.setOnAction((ActionEvent ev)->{
            sesso=genderTextField.getText();
            categoria=categoryTextField.getText();
            marca=brandTextField.getText();
            visualizzaTabellaProdotti();
            genderTextField.clear();
            categoryTextField.clear();
            brandTextField.clear();
        });
        btn.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-background-radius: 30;-fx-background-insets: 0;-fx-text-fill: white;");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);   
    }
    
    private void visualizzaBarraNavigazione(GridPane grid){
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(20);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(15);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(30);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setPercentWidth(15);
        ColumnConstraints column5 = new ColumnConstraints();
        column5.setPercentWidth(20);
        grid.getColumnConstraints().addAll(column1, column2,column3,column4,column5); // first column gets any extra width
        Label labelUsername = new Label("Utente:");
        Label labelUsernameValue = new Label(parametriConfigurazione.nomeUtente);
        VBox hbLabel = new VBox(5);
        hbLabel.getChildren().addAll(labelUsername,labelUsernameValue);
        grid.add(hbLabel,0,0);
        Label site = new Label("Sito Abbigliamento Sportivo");
        site.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        hbLabel.setAlignment(Pos.CENTER);
        site.setAlignment(Pos.CENTER);
        grid.add(site,2,0);
        Button btn1 = new Button("Ordini");
        btn1.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-background-radius: 30;-fx-background-insets: 0;-fx-text-fill: white;");
        Button btn2 = new Button("Carrello Spesa");
        btn2.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-background-radius: 30;-fx-background-insets: 0;-fx-text-fill: white;");
        btn2.setOnAction((ActionEvent ev)->{
            messaggioOrdine.setText("");
            visualizzaCarrelloSpesa();           
        });
        btn1.setOnAction((ActionEvent ev)->{
            boxVoti=null;
            tabellaProdottiOrdine=null;
            visualizzaStoricoOrdini();});
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().addAll(btn1,btn2);
        grid.add(hbBtn,4,0);     
    }
    
    private static void rimuoviBoxPrecedente(){   
        if(boxMaster!=null){
            root.getChildren().remove(boxMaster);
        }   
    }
    
    public static void visualizzaTabellaProdotti(){   
        rimuoviBoxPrecedente();
        if(!disabilitatoInvioEventoGUI)
          generaEventoGUI(CERCA);
        nomeScenario="vistatabellaprodotti";
        tabellaProdotti=new TabellaProdotti();
        tabellaProdotti.visualizzaListaProdotti(ArchivioProdotti.caricaListaProdotti(sesso,categoria,marca));    
        VBox vbox1=new VBox();
        final Label label = new Label("Risultati ricerca");
        label.setStyle(" -fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 14px;"); 
        vbox1.getChildren().addAll(label,tabellaProdotti);
        vbox1.setAlignment(Pos.CENTER);
        vbox1.setSpacing(15);  
        ObservableList<PieChart.Data> ol=FXCollections.observableArrayList();
        ol.addAll(ArchivioProdotti.caricaFetteGrafico(parametriConfigurazione.numeroGiorniGraficoTorta,parametriConfigurazione.numeroProdottiGraficoTorta,sesso,categoria,marca));
        final PieChart chart = new PieChart(ol);
        chart.setLabelsVisible(false);
        final Label title=new Label("Prodotti più venduti");
        title.setStyle(" -fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 14px;"); 
        VBox vbox2=new VBox();
        vbox2.getChildren().addAll(title,chart);
        vbox2.setAlignment(Pos.CENTER);
        GridPane grid=new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));
        ColumnConstraints column1=new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2=new ColumnConstraints();
        column2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(column1,column2);
        grid.add(vbox1, 0, 0);
        grid.add(vbox2,1,0);
        grid.setAlignment(Pos.CENTER);
        grid.setPrefSize(800,550);
        grid.setStyle(" -fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 8px;"); 
        grid.setLayoutX(400);
        grid.setLayoutY(115);
        boxMaster=new Group(grid);
        root.getChildren().add(boxMaster);
    }
    
     public static void visualizzaProdotto(){    
        rimuoviBoxPrecedente();
        if(!disabilitatoInvioEventoGUI)
          generaEventoGUI(SELEZIONEPRODOTTO);
        nomeScenario="vistaprodotto";
        String marca=null;
        String macroCategoria=null;
        String fotoModello=null;
        Double altezzaModello=0.0;
        String tagliaModello=null;
        String colore1=null;
        String colore2=null;
        String colore3=null;
        try{
        ResultSet rs=ArchivioProdotti.caricaInformazioniProdotto(nomeProdotto);
        while(rs.next()){
         marca=rs.getString("marca");
         macroCategoria=rs.getString("macrocategoria");
         prezzo=rs.getDouble("prezzo");
         fotoModello=rs.getString("fotomodello");
         altezzaModello=rs.getDouble("altezzamodello");
         tagliaModello=rs.getString("tagliamodello");
         colore1=rs.getString("colore1");
         colore2=rs.getString("colore2");
         colore3=rs.getString("colore3");
          }
        }
        catch(SQLException e){System.err.println(e.getMessage());}
        HBox hboxColori=new HBox();
        HBox hboxTaglie=new HBox();
        HBox hboxQuantita=new HBox();
        creaCampiSelezioneProdotto(colore1,colore2,colore3,hboxColori,hboxTaglie,hboxQuantita);
        Button aggiungiCarrello=new Button("AGGIUNGI AL CARRELLO");
        aggiungiCarrello.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-background-radius: 30;-fx-background-insets: 0;-fx-text-fill: white;");
        aggiungiCarrello.setOnAction((ActionEvent ev)->{aggiungiAlCarrello();});  
        final VBox vbox1=new VBox();
        ImageView image=new ImageView("file:../../myfiles/"+fotoModello);
        image.setFitWidth(288);
        image.setFitHeight(354);
        image.setStyle(" -fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;"); 
        HBox hbox1=new HBox();
        Label labelAltezzaModello=new Label("altezza modello: "+altezzaModello);
        labelAltezzaModello.setStyle(" -fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;"); 
        Label labelTagliaModello=new Label("tagliamodello: "+tagliaModello);
        labelTagliaModello.setStyle(" -fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;"); 
        hbox1.getChildren().addAll(labelAltezzaModello,labelTagliaModello);
        hbox1.setSpacing(10);
        hbox1.setAlignment(Pos.CENTER);
        Label guidaAlleTaglie=new Label("Guida alle taglie");
        guidaAlleTaglie.setStyle(" -fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;"); 
        tabellaMisure=new TabellaMisure(macroCategoria);
        tabellaMisure.visualizzaMisure(ArchivioProdotti.caricaTaglieProdotto(nomeProdotto));
        vbox1.getChildren().addAll(image,hbox1,guidaAlleTaglie,tabellaMisure);
        vbox1.setSpacing(10);
        vbox1.setAlignment(Pos.CENTER);
        final VBox vbox2=new VBox();
        Label labelNomeProdotto=new Label("Nome del prodotto: "+nomeProdotto);
        labelNomeProdotto.setStyle(" -fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;");   
        Label labelMarca=new Label("Marca: "+marca);
        labelMarca.setStyle(" -fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;"); 
        Label labelPrezzo=new Label("Prezzo: "+prezzo);
        labelPrezzo.setStyle(" -fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;"); 
        Label voto=new Label(ArchivioOrdini.votoMedioUtenti(nomeProdotto));
        voto.setStyle(" -fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;"); 
        messaggioAggiungiCarrello.setStyle("-fx-text-alignment:center");
        messaggioAggiungiCarrello.setText("");
        vbox2.getChildren().addAll(labelNomeProdotto,labelMarca,labelPrezzo,voto,hboxColori,hboxTaglie,hboxQuantita,aggiungiCarrello,messaggioAggiungiCarrello);
        vbox2.setSpacing(15);
        vbox2.setAlignment(Pos.CENTER);
        HBox hbox2=new HBox();
        hbox2.getChildren().addAll(vbox1,vbox2);
        hbox2.setSpacing(20);
        hbox2.setAlignment(Pos.CENTER);
        hbox2.setLayoutX(400);
        hbox2.setLayoutY(115);
        hbox2.setStyle(" -fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 14px;");
        hbox2.setPrefSize(600,580);
      
        boxMaster=new Group(hbox2);
        root.getChildren().add(boxMaster);  
    }
 
    private static void creaCampiSelezioneProdotto(String colore1,String colore2,String colore3,HBox hbox1,HBox hbox2,HBox hbox3){
        Label colore=new Label("Colori:");
        colore.setStyle(" -fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;"); 
        ToggleButton buttonColore1 = new ToggleButton(colore1);
        buttonColore1.setSelected(true);
        buttonColore1.setToggleGroup(group1);
        buttonColore1.setUserData(colore1);
        ToggleButton buttonColore2 = new ToggleButton(colore2);
        buttonColore2.setToggleGroup(group1);
        buttonColore2.setUserData(colore2);
        ToggleButton buttonColore3 = new ToggleButton(colore3);
        buttonColore3.setToggleGroup(group1);
        buttonColore3.setUserData(colore3);
        hbox1.getChildren().addAll(colore,buttonColore1,buttonColore2,buttonColore3);
        hbox1.setSpacing(5);
        hbox1.setAlignment(Pos.CENTER);
        Label taglie=new Label("Taglie:");
        taglie.setStyle(" -fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;"); 
        ToggleButton taglia1 = new ToggleButton("S");
        taglia1.setSelected(true);
        taglia1.setToggleGroup(group2);
        taglia1.setUserData("S");
        ToggleButton taglia2 = new ToggleButton("M");
        taglia2.setToggleGroup(group2);
        taglia2.setUserData("M");
        ToggleButton taglia3 = new ToggleButton("L");
        taglia3.setToggleGroup(group2);
        taglia3.setUserData("L");
        ToggleButton taglia4= new ToggleButton("XL");
        taglia4.setToggleGroup(group2);
        taglia4.setUserData("XL");
        ToggleButton taglia5=new ToggleButton("XXL");
        taglia5.setToggleGroup(group2);
        taglia5.setUserData("XXL");
        hbox2.getChildren().addAll(taglie,taglia1,taglia2,taglia3,taglia4,taglia5);
        hbox2.setSpacing(5);
        hbox2.setAlignment(Pos.CENTER);
        Label quantita=new Label("Quantità: ");
        quantita.setStyle(" -fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 10px;"); 
        hbox3.getChildren().addAll(quantita,quantityTextField);
        hbox3.setSpacing(5);
        hbox3.setAlignment(Pos.CENTER);
    }
    
    private static void aggiungiAlCarrello(){
        generaEventoGUI(AGGIUNGIALCARRELLO);
        String c=quantityTextField.getText();
        if(c.equals("")){
            messaggioAggiungiCarrello.setText("Specificare la quantità desiderata");
            return;
        }
        int quantitaRichiesta=Integer.parseInt(quantityTextField.getText());
        if(quantitaTotaleArticoli+quantitaRichiesta>parametriConfigurazione.massimaQuantitaProdottiCarrello){
           messaggioAggiungiCarrello.setText("Spazio nel carrello non sufficiente\n(Massimo "+parametriConfigurazione.massimaQuantitaProdottiCarrello+" articoli)");
           return;
        }
        if(quantitaRichiesta<=0){
            messaggioAggiungiCarrello.setText("Valore non ammissibile");
            return;
        }
        String colore=group1.getSelectedToggle().getUserData().toString();
        String taglia=group2.getSelectedToggle().getUserData().toString(); 
        messaggioAggiungiCarrello.setText("Aggiunto al carrello:\n"+nomeProdotto+","+colore+","+taglia);
        int index=cercaArticoloNelCarrello(colore,taglia);
        if(index!=-1)
          listaArticoliCarrello.set(index,new ArticoloCarrello(nomeProdotto,colore,quantitaRichiesta+quantitaNelCarrello,taglia,prezzo*(quantitaRichiesta+quantitaNelCarrello)));
            else  
          listaArticoliCarrello.add(new ArticoloCarrello(nomeProdotto,colore,quantitaRichiesta,taglia,prezzo*quantitaRichiesta));
        prezzoTotaleArticoli+=prezzo*quantitaRichiesta;
        quantitaTotaleArticoli+=quantitaRichiesta;
    }
    
      
    private static int cercaArticoloNelCarrello(String colore,String taglia){
        int index=-1;
        for(ArticoloCarrello ac:listaArticoliCarrello)
                if((ac.getNomeprodotto().equals(nomeProdotto))&&(ac.getColore().equals(colore))&&(ac.getTaglia().equals(taglia))){
                    quantitaNelCarrello=ac.getQuantita();
                    index=listaArticoliCarrello.indexOf(ac);
                }
                else{
                    continue;
                }
        return index;
    }
    
    
    public static void visualizzaCarrelloSpesa(){
        nomeScenario="vistacarrellospesa";
        rimuoviBoxPrecedente();
        if(!disabilitatoInvioEventoGUI)
          generaEventoGUI(CARRELLOSPESA);
        VBox vbox1=new VBox();
        Label labelNomeSped=new Label("Nome spedizione: ");
        Label labelIndirizzoSped=new Label("Indirizzo spedizione: ");
        Label labelCap=new Label("Cap: ");
        Label labelCittaSped=new Label("Città: ");
        Button inviaOrdine=new Button("INVIA ORDINE");
        inviaOrdine.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-background-radius: 30;-fx-background-insets: 0;-fx-text-fill: white;");
        inviaOrdine.setOnAction((ActionEvent ev)->{inviaOrdine();});
        messaggioOrdine.setStyle("-fx-text-alignment:center");
        vbox1.setAlignment(Pos.CENTER);
        vbox1.setSpacing(8);
        vbox1.getChildren().addAll(labelNomeSped,nomeSped,labelIndirizzoSped,indirizzoSped,labelCap,cap,labelCittaSped,cittaSped,inviaOrdine,messaggioOrdine);       
        VBox vbox2=new VBox();
        Label carrello=new Label("CARRELLO SPESA");
        tabellaCarrelloSpesa=new TabellaCarrelloSpesa();
        tabellaCarrelloSpesa.visualizzaArticoliCarrello(listaArticoliCarrello);
        totale=new Label("Totale: "+Double.toString(prezzoTotaleArticoli));
        Label labelArticoloDaRimuovere=new Label("ARTICOLO DA RIMUOVERE: ");
        Label labelNomeArticoloDaRimuovere=new Label("NOME ARTICOLO");
        Label labelColoreArticoloDaRimuovere=new Label("COLORE ARTICOLO");
        Label labelTagliaArticoloDaRimuovere=new Label("TAGLIA ARTICOLO");
        Button rimuovi=new Button("RIMUOVI ARTICOLO");
        rimuovi.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-background-radius: 30;-fx-background-insets: 0;-fx-text-fill: white;");
        rimuovi.setOnAction((ActionEvent ev)->{rimuoviArticoloCarrello();});
        messaggioRimuoviCarrello.setStyle("-fx-text-alignment:center");
        messaggioRimuoviCarrello.setText("");
        vbox2.setAlignment(Pos.CENTER);
        vbox2.setSpacing(5);
        vbox2.getChildren().addAll(carrello,tabellaCarrelloSpesa,totale,labelArticoloDaRimuovere,labelNomeArticoloDaRimuovere,nomeArticoloDaRimuovere,labelColoreArticoloDaRimuovere,coloreArticoloDaRimuovere,labelTagliaArticoloDaRimuovere,tagliaArticoloDaRimuovere,rimuovi,messaggioRimuoviCarrello);
        HBox hbox=new HBox();
        hbox.getChildren().addAll(vbox1,vbox2);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        hbox.setPrefSize(800,550);
        hbox.setLayoutX(400);
        hbox.setLayoutY(115);
        hbox.setStyle(" -fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 14px;");
        boxMaster=new Group(hbox);
        root.getChildren().add(boxMaster);
    }
    
    private static void inviaOrdine(){
        generaEventoGUI(INVIAORDINE);
        String nomeSpedizione=nomeSped.getText();
        String indirizzoSpedizione=indirizzoSped.getText();
        String capSpedizione=cap.getText();
        String cittaSpedizione=cittaSped.getText(); 
        if(nomeSpedizione.equals("")||indirizzoSpedizione.equals("")||capSpedizione.equals("")||cittaSpedizione.equals("")){
           messaggioOrdine.setText("Tutti i campi vanno riempiti");
           return;
        }
        if(listaArticoliCarrello.isEmpty()){
           messaggioOrdine.setText("Carrello vuoto,invio ordine negato");  
           return;
        }
        nomeSped.clear();
        indirizzoSped.clear();
        cap.clear();
        cittaSped.clear();
        int codOrdine=ArchivioOrdini.inserisciOrdine(parametriConfigurazione.nomeUtente,nomeSpedizione,indirizzoSpedizione,capSpedizione,cittaSpedizione);
        ArchivioOrdini.inserisciProdottiOrdine(codOrdine);
        messaggioOrdine.setText("Il tuo ordine è stato effettuato.\n"+"Il codiceordine è: "+codOrdine);
        prezzoTotaleArticoli=0;
        quantitaTotaleArticoli=0;
        totale.setText("Totale: "+Double.toString(prezzoTotaleArticoli));
        tabellaCarrelloSpesa.visualizzaArticoliCarrello(listaArticoliCarrello);          
    }
    
    private static void rimuoviArticoloCarrello(){ 
        generaEventoGUI(RIMUOVIARTICOLO);
        String nomeProdotto=nomeArticoloDaRimuovere.getText();
        String colore=coloreArticoloDaRimuovere.getText();
        String taglia=tagliaArticoloDaRimuovere.getText();
        int index=cercaArticoloNelCarrello(colore,taglia);
        if(index==-1){
           messaggioRimuoviCarrello.setText("Nessun articolo corrispondente");
           return;
        }
        ArticoloCarrello ac=listaArticoliCarrello.remove(index);
        double prezzoArticoloCarrello=ac.getPrezzo();
        nomeArticoloDaRimuovere.clear();
        coloreArticoloDaRimuovere.clear();
        tagliaArticoloDaRimuovere.clear();
        messaggioRimuoviCarrello.setText("Articolo: "+nomeProdotto+","+colore+","+taglia+"\nrimosso con successo");
        tabellaCarrelloSpesa.visualizzaArticoliCarrello(listaArticoliCarrello);
        prezzoTotaleArticoli-=prezzoArticoloCarrello;
        quantitaTotaleArticoli-=quantitaNelCarrello;
        totale.setText("Totale: "+Double.toString(prezzoTotaleArticoli));
     }
    
    
    public static void visualizzaStoricoOrdini(){
        nomeScenario="vistaordini";
        rimuoviBoxPrecedente();
        if(!disabilitatoInvioEventoGUI)
          generaEventoGUI(ORDINI);
        boxTabellaOrdini=new VBox();
        Label ordini=new Label("ORDINI");
        TabellaOrdini tab=new TabellaOrdini();
        tab.visualizzaOrdini(ArchivioOrdini.caricaListaOrdini(parametriConfigurazione.nomeUtente));
        boxTabellaOrdini.setPrefSize(800,550); //600
        boxTabellaOrdini.setLayoutX(400);
        boxTabellaOrdini.setLayoutY(115);
        boxTabellaOrdini.setAlignment(Pos.CENTER);
        boxTabellaOrdini.setSpacing(10);
        boxTabellaOrdini.getChildren().addAll(ordini,tab);
        boxTabellaOrdini.setStyle(" -fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0;-fx-text-fill: #654b00;-fx-font-weight: bold;-fx-font-size: 14px;");
        boxMaster=new Group(boxTabellaOrdini);
        root.getChildren().add(boxMaster);    
    }
    
    public static void visualizzaProdottiOrdine(int codice){
        nomeScenario="vistaprodottiordine";
        if(!disabilitatoInvioEventoGUI)
          generaEventoGUI(MOSTRAPRODOTTIORDINE);
        boolean giaCaricataTabella=false;
        boolean giaCaricatoOrdine=false;
        if(codiceOrdine==codice)
          giaCaricatoOrdine=true;
        else
          codiceOrdine=codice;
        if(tabellaProdottiOrdine!=null)
          giaCaricataTabella=true;
        if(giaCaricataTabella)
          labelProdottiOrdine.setText("Prodotti relativi all'ordine "+codiceOrdine);
        else
          labelProdottiOrdine=new Label("Prodotti relativi all'ordine "+codiceOrdine);
        if(!giaCaricataTabella){
          tabellaProdottiOrdine=new TabellaProdottiOrdine();
          boxTabellaOrdini.getChildren().addAll(labelProdottiOrdine,tabellaProdottiOrdine);
        }
        tabellaProdottiOrdine.visualizzaListaProdottiOrdine(ArchivioOrdini.caricaListaProdottiOrdine(codiceOrdine));
        if(boxVoti!=null&&!giaCaricatoOrdine){
          boxTabellaOrdini.getChildren().remove(boxVoti);
          boxVoti=null;
        }        
    }
    
    
    public static void visualizzaAssegnaVoto(){
        nomeScenario="vistaassegnavoto";
        if(!disabilitatoInvioEventoGUI)
          generaEventoGUI(ASSEGNAVOTOPRODOTTO);
        if(boxVoti==null){
          ToggleButton voto1 = new ToggleButton("1");
          voto1.setToggleGroup(group);
          voto1.setSelected(true);
          voto1.setUserData("1");
          ToggleButton voto2 = new ToggleButton("2");
          voto2.setToggleGroup(group);
          voto2.setUserData("2");
          ToggleButton voto3 = new ToggleButton("3");
          voto3.setToggleGroup(group);
          voto3.setUserData("3");
          ToggleButton voto4= new ToggleButton("4");
          voto4.setToggleGroup(group);
          voto4.setUserData("4");
          ToggleButton voto5=new ToggleButton("5");
          voto5.setToggleGroup(group);
          voto5.setUserData("5");
          HBox voti=new HBox();
          voti.getChildren().addAll(voto1,voto2,voto3,voto4,voto5);
          voti.setAlignment(Pos.CENTER);
          bottoneRegistraVoto.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-background-radius: 30;-fx-background-insets: 0;-fx-text-fill: white;");
          boxVoti=new VBox();
          boxVoti.getChildren().addAll(labelAssegnaVoto,voti,bottoneRegistraVoto);
          boxVoti.setAlignment(Pos.CENTER);
          boxVoti.setSpacing(10);
          boxTabellaOrdini.getChildren().add(boxVoti);
        }
        labelAssegnaVoto.setText("Assegna un voto a "+prodottoOrdine+" colore "+coloreProdottoOrdine+" taglia "+tagliaProdottoOrdine+".\nSe hai già assegnato un voto,questo verrà sovrascritto");
        bottoneRegistraVoto.setOnAction((ActionEvent ev)->{
          generaEventoGUI(REGISTRAVOTO);
          String votoselezionato=group.getSelectedToggle().getUserData().toString();
          ArchivioOrdini.registraVoto(codiceOrdine,prodottoOrdine,coloreProdottoOrdine,tagliaProdottoOrdine,Integer.parseInt(votoselezionato));
          tabellaProdottiOrdine.visualizzaListaProdottiOrdine(ArchivioOrdini.caricaListaProdottiOrdine(codiceOrdine));
        });   
    }
    
    private void chiudiApplicazione(){
        generaEventoGUI(TERMINE);
        cacheCarrelloSpesa.scrivi(listaArticoliCarrello);
        ControlloreCacheScenario.salvaStato();
    }
     
    public static void main(String[] args) {
        launch(args);
    }  
}

/*
Note:
(1): Il metodo start nel suo complesso è quello che fa partire l'applicazione:
     legge dal file di configurazione i parametri di configurazione;crea un istanza di Cache
     TabellaCarrelloSpesa con l'etichetta apposita e riempe il carrello spesa leggendo dal 
     file binario;invoca la generaEventoGUI;istanzia le griglie di scena e invoca i metodi
     per riempirle;assegna l'event handler di chiusura della finestra;prepara il boxpadre 
     che andrà a contenere i box delle varie viste(prodotti,ordine e carrello spesa);
     legge dal file binario l'ultimo stato salvato e l'applicazione riprende da esso.
     La prima volta si parte da "vistapaginainiziale",valore con cui viene settata la 
     variabile nomescenario(verrà chiarito poi).

(2): Il metodo generaEventoGUI crea un EventoGUI con l'etichetta apposita e lo invia
     al server.

(3): visualizzaCampoRicerca riceve la griglia che andrà ad allestire: aggiunge ad essa i tre
     campi testo più il bottone per la ricerca del prodotto e assegna a quest'ultimo
     l'event handler apposito.

(4): visualizzaBarraNavigazione anch'esso riceve la griglia da riempire: la allestisce
     mettendo il nome dell'utente loggato , il nome dell'applicazione e due bottoni a
     cui vengono assegnati rispettivamente gli handler per la visualizzazione dello storico
     ordini e del carrello spesa.

(5): rimuoviBoxPrecedente sostanzialmente rimuove la vista precedente per lasciar spazio
     a una nuova vista. Per questo i metodi visualizza che verranno a seguire la
     invocano per poi effettuare l'aggancio della nuova vista.

(6): visualizzaTabellaProdotti esegue le seguenti azioni:istanzia la tabella dei prodotti e il
     grafico a torta; riempe quest'ultimi invocando gli appositi metodi delle classi backend.

(7): visualizzaProdotto:invoca il metodo della classe backend 
     ArchivioProdotti per ottenere tutte le informazioni che riguardano il prodotto;
     invoca il metodo creaCampiSelezioneProdotto per creare i vari controlli;
     istanzia gli altri oggetti necessari ad avere una completa visuale del prodotto(tabella 
     delle taglie , altezza del modello, prezzo e nome del prodotto....);infine assegna 
     l'event handler per l'aggiunta dell'articolo al carrello a un apposito bottone.

(8): creaCampiSelezioneProdotto crea i radiocomandi per la scelta del colore e per la scelta
     della taglia e infine crea il campo testo per esprimere la quantità desiderata che
     si vuole acquistare.

(9): la aggiungiAlCarrello controlla che il valore inserito nel campo testo per la quantità
     sia ammissibile o che essa non superi la capienza disponibile attuale del carrello
     spesa.Inoltre controlla se il prodotto è già presente nel carrello e in quel caso, non
     fa una semplice aggiunta ma sostituisce incrementando la quantità presente nel carrello
     di un valore pari a quella ora richiesta.

(10): cercaArticoloNelCarrello scorre la lista del carrello spesa controllando se esiste un
      prodotto analogo a quello cercato(cioè coincidente per nome, colore e taglia.
      Il metodo viene utilizzato anche della rimuoviCarrello.

(11): visualizzaCarrelloSpesa: crea la tabella che contiene gli articoli del carrello e la 
      riempe;crea i campi testo per l'invio dell'ordine e per la rimozione dell'articolo 
     dal carrello e i bottoni; assegna a questi bottoni gli handler corrispondenti.

(12): inviaOrdine controlla che tutti i campi siano stati inseriti e che il carrello spesa
      non sia vuoto prima di inoltrare l'ordine.Invoca due metodi della classe di backend
      ArchivioOrdini: inserisciOrdine , che inserisce l'ordine e ne restituisce il codice, e
      la inserisciProdottiOrdine(che appunto richiede l'identificativo dell'ordine).

(13): visualizzaStoricoOrdini crea la tabella contenente tutti gli ordini effettuati
      dall'utente.La pressione del pulsante MOSTRA PRODOTTI invoca la visualizzaProdottiOrdine.
      
(14): visualizzaProdottiOrdine crea la tabella contenente i prodotti relativi a un dato ordine.
      La pressione del pulsante col valore del voto invoca la visualizzaAssegnaVoto.

(15): visualizzaAssegnaVoto crea il ToggleGroup e il bottone per la registrazione del voto e
      assegna l'handler al pulsante REGISTRA VOTO che invoca l'opportuno metodo della classe
      backend ArchivioOrdini.

(16): chiudiApplicazione invoca la generaEventoGUI passando l'etichetta di fine e salva su
      file binario le informazioni che riguardano la cache e la vista corrente.
      
*/