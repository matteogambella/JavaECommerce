
import java.sql.*;
import java.util.*;
import java.time.LocalDate;

public class ArchivioOrdini {
    
    private static Connection con;
    
    static{
        try{
        con=DriverManager.getConnection("jdbc:mysql://"+SitoAbbigliamentoSportivo.parametriConfigurazione.ipServerDBMS+":"+SitoAbbigliamentoSportivo.parametriConfigurazione.portaServerDBMS+"/sitoabbigliamento","root","");
        }
        catch(SQLException e){System.err.println(e.getMessage());}
    }
    
    public static int inserisciOrdine (String us,String ns,String is,String cap,String cs){
        int codordine=0;
        try{
            PreparedStatement pst=con.prepareStatement("INSERT INTO Ordine(cliente,datainvioordine,nomespedizione,indirizzospedizione,cap,cittaspedizione)VALUES(?,?,?,?,?,?)");
            pst.setString(1,us);
            pst.setString(2,LocalDate.now().toString());
            pst.setString(3,ns);
            pst.setString(4,is);
            pst.setString(5,cap);
            pst.setString(6,cs);
            pst.executeUpdate();
            pst=con.prepareStatement("SELECT MAX(codiceordine)as codice FROM ordine");
            ResultSet rs=pst.executeQuery();
            rs.next();
            codordine=rs.getInt("codice");
        }
        catch(SQLException e){System.err.println(e.getMessage());}
        return codordine;
    }
    
    public static void inserisciProdottiOrdine(int co){
        try{
            PreparedStatement pst=con.prepareStatement("INSERT INTO articoloordine VALUES(?,?,?,?,?,?)");
            pst.setInt(1,co);
            while (!SitoAbbigliamentoSportivo.listaArticoliCarrello.isEmpty()){
               ArticoloCarrello ac=SitoAbbigliamentoSportivo.listaArticoliCarrello.remove(0);
               pst.setString(2,ac.getNomeprodotto());
               pst.setString(3,ac.getColore());
               pst.setInt(4,ac.getQuantita());
               pst.setString(5,ac.getTaglia());
               pst.setString(6,null);
               pst.executeUpdate();
            }  
        }catch(SQLException e){System.err.println(e.getMessage());}
    }
   
    public static List<Ordine> caricaListaOrdini(String us){
        List<Ordine> listaordini=new ArrayList<>();
        try{
           PreparedStatement pst=con.prepareStatement("SELECT codiceordine,datainvioordine,nomespedizione,indirizzospedizione,cap FROM ordine WHERE cliente=?");
           pst.setString(1,us);
           ResultSet rs=pst.executeQuery();
           while(rs.next()){
               listaordini.add(new Ordine(rs.getInt("codiceordine"),rs.getString("datainvioordine"),rs.getString("nomespedizione"),rs.getString("indirizzospedizione"),rs.getString("cap")));
           }
        }catch(SQLException e){System.err.println(e.getMessage());}
        return listaordini;
    }
   
    public static List<ProdottoOrdine> caricaListaProdottiOrdine(int co){
        List<ProdottoOrdine> listaprodottiordine = new ArrayList<>();
        try{
           PreparedStatement pst=con.prepareStatement("SELECT nomeprodotto,colore,quantita,taglia,voto FROM articoloordine WHERE codiceordine=?");
           pst.setInt(1,co);
           ResultSet rs=pst.executeQuery();
           while (rs.next()){
               listaprodottiordine.add(new ProdottoOrdine(rs.getString("nomeprodotto"),rs.getString("colore"),rs.getInt("quantita"),rs.getString("taglia"),rs.getString("voto")));
           }
        }catch(SQLException e){System.err.println(e.getMessage());}
        return listaprodottiordine;
    }
   
    public static void registraVoto(int co,String np,String clr,String tgl,int vt){
        try{
           PreparedStatement pst=con.prepareStatement("UPDATE articoloordine SET voto=? WHERE codiceordine=? AND nomeprodotto=? AND colore=? AND taglia=?");
           pst.setInt(1,vt);
           pst.setInt(2,co);
           pst.setString(3,np);
           pst.setString(4,clr);
           pst.setString(5,tgl);
           pst.executeUpdate();
        }catch(SQLException e){System.err.println(e.getMessage());}
    }
   
    public static String votoMedioUtenti(String np){
        double votomedio=0;
        try{
           PreparedStatement pst=con.prepareStatement("SELECT AVG(voto) as votomedio FROM articoloordine WHERE nomeprodotto=?");
           pst.setString(1,np);
           ResultSet rs=pst.executeQuery();
           rs.next();
           votomedio=rs.getDouble("votomedio");
        }
        catch(SQLException e){System.err.println(e.getMessage());}
        String votoutenti=Double.toString(votomedio);
        if(votoutenti.equals("0.0"))
            votoutenti="Nessun voto registrato per quest'articolo";
        else
            votoutenti="Voto medio degli utenti: "+votoutenti;
        return votoutenti;
    }
}


/*
Note:

(1) inserisciOrdine registra l'ordine nel database e restituisce il codice corrispondente
    all'ordine effettuato.
(2) inserisciProdottiOrdine inserisce una alla volta gli articoli del carrello nel database
    rimuovendoli dalla lista del carrello spesa.Il carrello spesa alla fine risulta dunque
    svuotato.
(3) caricaListaOrdini legge dal database e restituisce la lista di ordini di un determinato
    utente.
(4) caricaListaProdottiOrdine legge dal database e restituisce la lista dei prodotti di un 
    determinato ordine.
(5) registraVoto memorizza nel database il voto espresso da un utente su un prodotto 
    precedentemente acquistato.Il voto non è definitivo, si potrà esprimere ancora una
    volta un voto sovrascrivendo il precedente.Un utente può assegnare un voto diverso allo
    stesso prodotto(cioè con lo stesso nome) ma di taglia e colore diverso.
(6) votoMedioUtenti legge dal database la media dei voti espressi dagli utenti su un 
    determinato prodotto restituendo una stringa. Se nessun utente ha mai registrato un voto
    per quel prodotto la stringa restituita sarà "Nessun voto registrato per quest'articolo".
*/