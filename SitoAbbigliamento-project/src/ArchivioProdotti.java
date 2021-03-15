
import java.sql.*;
import java.util.*;
import javafx.scene.chart.PieChart;

public class ArchivioProdotti {

    private static Connection con;
    
    static{   
        try{
        con=DriverManager.getConnection("jdbc:mysql://"+SitoAbbigliamentoSportivo.parametriConfigurazione.ipServerDBMS+":"+SitoAbbigliamentoSportivo.parametriConfigurazione.portaServerDBMS+"/sitoabbigliamento","root","");
        }
        catch(SQLException e){System.err.println(e.getMessage());}
    }
    
    private static final String queryGraficoTorta=""+
    "SELECT P1.nomeprodotto,ROUND(SUM(A1.quantita)/\n"+
    "        (SELECT SUM(A2.quantita)\n"+
    "         FROM prodotti P2 NATURAL JOIN  articoloordine A2 NATURAL JOIN ordine O2\n"+
    "         WHERE DATE_SUB(current_date,INTERVAL ? DAY)<=O2.datainvioordine\n"+
    "               AND P2.sesso=? AND P2.categoria=? AND P2.marca=?)*100,2)as percentuale\n"+
    "FROM prodotti P1 NATURAL JOIN articoloordine A1 NATURAL JOIN ordine O1\n"+
    "WHERE DATE_SUB(current_date,INTERVAL ? DAY)<=O1.datainvioordine\n"+
    "      AND P1.sesso=? AND P1.categoria=? AND P1.marca=?\n"+
    "GROUP BY P1.nomeprodotto ORDER BY percentuale DESC limit ?";
    
    public static List<Prodotto> caricaListaProdotti(String s,String c,String m){
        List<Prodotto> listaProdotti=new ArrayList<>();
        try{
            PreparedStatement pst=con.prepareStatement("SELECT nomeprodotto,fotomodello FROM prodotti where sesso=? AND marca=? AND categoria=?");
            pst.setString(1,s);
            pst.setString(2,m);
             pst.setString(3,c);
            ResultSet rs=pst.executeQuery();
            while (rs.next())                
                listaProdotti.add(new Prodotto(rs.getString("nomeprodotto"),rs.getString("fotomodello")));        
        } 
        catch(SQLException e){System.err.println(e.getMessage());}
        return listaProdotti;
    }
    
    public static ResultSet caricaInformazioniProdotto(String np){
        ResultSet rs=null;
        try{
            PreparedStatement pst=con.prepareStatement("SELECT macrocategoria,marca,prezzo,fotomodello,altezzamodello,tagliamodello,colore1,colore2,colore3 FROM prodotti NATURAL JOIN associacategoria where nomeprodotto=?");
            pst.setString(1,np);
            rs=pst.executeQuery();
        }
        catch(SQLException e){System.err.println(e.getMessage());}
        return rs;
    }

    public static List<MisureTaglie> caricaTaglieProdotto(String np){
        List<MisureTaglie> listaMisureTaglie=new ArrayList<>();
        try{
            PreparedStatement pst=con.prepareStatement("SELECT taglia,valoremisura1,valoremisura2,valoremisura3 FROM guidataglie NATURAL JOIN associacategoria NATURAL JOIN prodotti WHERE nomeprodotto=? order by valoremisura1");
            pst.setString(1,np);
            ResultSet rs=pst.executeQuery();
            while(rs.next())
              listaMisureTaglie.add(new MisureTaglie(rs.getString("taglia"),rs.getInt("valoremisura1"),rs.getInt("valoremisura2"),rs.getInt("valoremisura3")));
        }
        catch(SQLException e){System.err.println(e.getMessage());}
        return listaMisureTaglie;
    }
    
    public static List<PieChart.Data> caricaFetteGrafico(int ng,int np,String s,String c,String m){
        List<PieChart.Data> listaFette=new ArrayList<>();
        double othersPercentage=100;
        try{
            PreparedStatement pst=con.prepareStatement(queryGraficoTorta);
            pst.setInt(1,ng);
            pst.setString(2,s);
            pst.setString(3,c);
            pst.setString(4,m);
            pst.setInt(5,ng);
            pst.setString(6,s);
            pst.setString(7,c);
            pst.setString(8,m);
            pst.setInt(9,np);
            ResultSet rs= pst.executeQuery();
        while(rs.next()){
              double percentage=rs.getDouble("percentuale");
              listaFette.add(new PieChart.Data(rs.getString("nomeprodotto")+"("+Double.toString(percentage)+"%)",percentage)); 
              othersPercentage-=percentage;
        }
        if(othersPercentage>0.1)
              listaFette.add(new PieChart.Data("Altro("+String.format("%.2f",othersPercentage)+"%)",othersPercentage));
        }catch(SQLException e){System.err.println(e.getMessage());}
        return listaFette;
    }
}

/*
Note:

(1) caricaListaProdotti legge nel database e restituisce una lista di prodotti che 
    soddisfano i criteri di ricerca del prodotto cioè sesso,categoria e marca.
(2) caricaInformazioniProdotto legge nel database e restituisce una lista di informazioni
    che riguardano un determinato prodotto e ne restituisce il resultset.
(3) caricaTaglieProdotto legge nel database i valori delle taglie associate al prodotto
    (XS,S,M,L,XL).I valori delle misure delle taglie dipendono dalla marca e dalla macrocategoria
    (Bottom o Top).In particolare dalla macrocategoria dipende anche quali sono le misure 
    prese in considerazione(basta vedere il costruttore della TabellaMisure).
(4) caricaFetteGrafico esegue la query contenuta nella stringa queryGraficoTorta e 
    restituisce una lista di oggetti PieChart.Data con nome quello del prodotto e valore
    la percentuale di acquisti di quel prodotto.Il periodo preso in considerazione e il 
    numero dei prodotti con più acquisti sono parametri di configurazione.Gli altri prodotti
    sono indicati nel grafico tutti insieme con un unico elemento con nome "Altro" e valore
    la loro percentuale totale.
    0.1 è una tolleranza scelta per evitare che errori di approssimazione possano essere 
    scambiati per altri prodotti.
*/