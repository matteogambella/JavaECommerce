
import java.util.List;
import javafx.scene.control.*;


public class ControlloreCacheScenario {
     
    private static final String FILE_CACHE_SCENARIO = "cache_scenario.bin";
    private static CacheScenario cacheScenario=new CacheScenario(FILE_CACHE_SCENARIO);

    public static void salvaStato(){
        switch(SitoAbbigliamentoSportivo.nomeScenario){
            case "vistapaginainiziale":case "vistatabellaprodotti":case "vistaprodotto": salvaScenarioProdotti();break;
            case "vistaordini":case "vistaprodottiordine":case "vistaassegnavoto":salvaScenarioOrdini();break;
            case "vistacarrellospesa":salvaScenarioCarrelloSpesa();       
        }
    }
    
    public static void caricaStato(){
        SitoAbbigliamentoSportivo.disabilitatoInvioEventoGUI=true;
        String[] info=cacheScenario.leggi();
        if(!info[0].equals("")){
          switch(info[0]){
              case "vistapaginainiziale":case "vistatabellaprodotti":case "vistaprodotto":caricaScenarioProdotti(info);break;
              case "vistaordini":case "vistaprodottiordine":case "vistaassegnavoto":caricaScenarioOrdini(info);break;
              case "vistacarrellospesa":caricaScenarioCarrelloSpesa(info);
          }
        }
        SitoAbbigliamentoSportivo.disabilitatoInvioEventoGUI=false;
    }
      
    private  static void salvaScenarioCarrelloSpesa(){
        String[] info=new String[13];
        info[0]=SitoAbbigliamentoSportivo.nomeScenario;
        info[1]=SitoAbbigliamentoSportivo.nomeSped.getText();
        info[2]=SitoAbbigliamentoSportivo.indirizzoSped.getText();
        info[3]=SitoAbbigliamentoSportivo.cap.getText();
        info[4]=SitoAbbigliamentoSportivo.cittaSped.getText();
        info[5]=SitoAbbigliamentoSportivo.messaggioOrdine.getText();
        info[6]=SitoAbbigliamentoSportivo.nomeArticoloDaRimuovere.getText();
        info[7]=SitoAbbigliamentoSportivo.coloreArticoloDaRimuovere.getText();
        info[8]=SitoAbbigliamentoSportivo.tagliaArticoloDaRimuovere.getText();
        info[9]=SitoAbbigliamentoSportivo.messaggioRimuoviCarrello.getText(); 
        info[10]=SitoAbbigliamentoSportivo.genderTextField.getText();
        info[11]=SitoAbbigliamentoSportivo.categoryTextField.getText();
        info[12]=SitoAbbigliamentoSportivo.brandTextField.getText();
        cacheScenario.scrivi(info);
    }
    
    private static void caricaScenarioCarrelloSpesa(String[] info){
        String i1=info[1];
        if(!i1.equals(""))
            SitoAbbigliamentoSportivo.nomeSped.setText(i1);
        String i2=info[2];
        if(!i2.equals(""))
            SitoAbbigliamentoSportivo.indirizzoSped.setText(i2);
        String i3=info[3];
        if(!i3.equals(""))
            SitoAbbigliamentoSportivo.cap.setText(i3);
        String i4=info[4];
        if(!i4.equals(""))
            SitoAbbigliamentoSportivo.cittaSped.setText(i4);
        String i5=info[5];
        if(!i5.equals(""))
            SitoAbbigliamentoSportivo.messaggioOrdine.setText(i5);
        String i6=info[6];
        if(!i6.equals(""))
            SitoAbbigliamentoSportivo.nomeArticoloDaRimuovere.setText(i6);
        String i7=info[7];
        if(!i7.equals(""))
            SitoAbbigliamentoSportivo.coloreArticoloDaRimuovere.setText(i7);
        String i8=info[8];
        if(!i8.equals(""))
            SitoAbbigliamentoSportivo.tagliaArticoloDaRimuovere.setText(i8);
        String i9=info[9];
        if(!i9.equals(""))
            SitoAbbigliamentoSportivo.messaggioRimuoviCarrello.setText(i9); 
        String i10=info[10];
        if(!i10.equals(""))
            SitoAbbigliamentoSportivo.genderTextField.setText(i10);
        String i11=info[11];
        if(!i11.equals(""))
            SitoAbbigliamentoSportivo.categoryTextField.setText(i11);
        String i12=info[12];
        if(!i12.equals(""))
            SitoAbbigliamentoSportivo.brandTextField.setText(i12); 
        SitoAbbigliamentoSportivo.visualizzaCarrelloSpesa();
    }
    
    private static void salvaScenarioOrdini(){
        String[] info=new String[10];
        info[0]=SitoAbbigliamentoSportivo.nomeScenario;
        info[1]=Integer.toString(SitoAbbigliamentoSportivo.codiceOrdine);
        info[2]=SitoAbbigliamentoSportivo.prodottoOrdine;
        info[3]=SitoAbbigliamentoSportivo.coloreProdottoOrdine;
        info[4]=SitoAbbigliamentoSportivo.tagliaProdottoOrdine;
        info[5]=SitoAbbigliamentoSportivo.labelAssegnaVoto.getText();
        if(SitoAbbigliamentoSportivo.group.getSelectedToggle()!=null)
          info[6]=SitoAbbigliamentoSportivo.group.getSelectedToggle().getUserData().toString();
        else
          info[6]="";  
        info[7]=SitoAbbigliamentoSportivo.genderTextField.getText();
        info[8]=SitoAbbigliamentoSportivo.categoryTextField.getText();
        info[9]=SitoAbbigliamentoSportivo.brandTextField.getText();
        cacheScenario.scrivi(info);  
    }
    
    private static void caricaScenarioOrdini(String[] info){
        String nomescenario=info[0];
        SitoAbbigliamentoSportivo.visualizzaStoricoOrdini();
        if(nomescenario.equals("vistaprodottiordine")||nomescenario.equals("vistaassegnavoto")){
          String i1=info[1];
          if(!i1.equals(""))
            SitoAbbigliamentoSportivo.codiceOrdine=Integer.parseInt(info[1]);
          SitoAbbigliamentoSportivo.visualizzaProdottiOrdine(SitoAbbigliamentoSportivo.codiceOrdine);
          if(nomescenario.equals("vistaassegnavoto")){
            String i2=info[2];
          if(!i2.equals(""))
            SitoAbbigliamentoSportivo.prodottoOrdine=info[2];
          String i3=info[3];
          if(!i3.equals(""))
            SitoAbbigliamentoSportivo.coloreProdottoOrdine=info[3];
          String i4=info[4];
          if(!i4.equals(""))
            SitoAbbigliamentoSportivo.tagliaProdottoOrdine=info[4];
          String i5=info[5];
          if(!i5.equals(""))
            SitoAbbigliamentoSportivo.labelAssegnaVoto.setText(info[5]);
          SitoAbbigliamentoSportivo.visualizzaAssegnaVoto();
          selezionaToggleButton(SitoAbbigliamentoSportivo.group,info[6]);
          }
        }
        String i7=info[7];
        if(!i7.equals(""))
          SitoAbbigliamentoSportivo.genderTextField.setText(info[7]);
        String i8=info[8];
        if(!i8.equals(""))
          SitoAbbigliamentoSportivo.categoryTextField.setText(info[8]);
        String i9=info[9];
        if(!i9.equals(""))
          SitoAbbigliamentoSportivo.brandTextField.setText(info[9]);
    }
    
    private static void selezionaToggleButton(ToggleGroup tg,String value){
        List<Toggle> l=tg.getToggles();
        for(Toggle toggle:l)
            if(toggle.getUserData().equals(value))
                toggle.setSelected(true);
            else
                continue;
    }
    
   
    private static void salvaScenarioProdotti(){
        String[] info=new String[12]; 
        info[0]=SitoAbbigliamentoSportivo.nomeScenario;
        info[1]=SitoAbbigliamentoSportivo.genderTextField.getText();
        info[2]=SitoAbbigliamentoSportivo.categoryTextField.getText();
        info[3]=SitoAbbigliamentoSportivo.brandTextField.getText();
        info[4]=SitoAbbigliamentoSportivo.sesso;
        info[5]=SitoAbbigliamentoSportivo.categoria;
        info[6]=SitoAbbigliamentoSportivo.marca;
        info[7]=SitoAbbigliamentoSportivo.nomeProdotto;
        if(SitoAbbigliamentoSportivo.group1.getSelectedToggle()!=null)
          info[8]=SitoAbbigliamentoSportivo.group1.getSelectedToggle().getUserData().toString();
        else
          info[8]="";
        if(SitoAbbigliamentoSportivo.group2.getSelectedToggle()!=null)
          info[9]=SitoAbbigliamentoSportivo.group2.getSelectedToggle().getUserData().toString();
        else
          info[9]="";
        info[10]=SitoAbbigliamentoSportivo.quantityTextField.getText();
        info[11]=SitoAbbigliamentoSportivo.messaggioAggiungiCarrello.getText();
        cacheScenario.scrivi(info);
    }
    
    private static void caricaScenarioProdotti(String[] info){
        String nomescenario=info[0];
        String i1=info[1];
        if(!i1.equals(""))
          SitoAbbigliamentoSportivo.genderTextField.setText(info[1]);
        String i2=info[2];
        if(!i2.equals(""))
          SitoAbbigliamentoSportivo.categoryTextField.setText(info[2]);
        String i3=info[3];
        if(!i3.equals(""))
          SitoAbbigliamentoSportivo.brandTextField.setText(info[3]);
        if(nomescenario.equals("vistatabellaprodotti")){
          String i4=info[4];
          if(!i4.equals(""))
            SitoAbbigliamentoSportivo.sesso=info[4];
          String i5=info[5];
          if(!i5.equals(""))
            SitoAbbigliamentoSportivo.categoria=info[5];
          String i6=info[6];
          if(!i6.equals(""))
            SitoAbbigliamentoSportivo.marca=info[6];
          SitoAbbigliamentoSportivo.visualizzaTabellaProdotti();
          }else{
           if(nomescenario.equals("vistaprodotto")){ 
            String i7=info[7];
            if(!i7.equals(""))
              SitoAbbigliamentoSportivo.nomeProdotto=info[7];
            SitoAbbigliamentoSportivo.visualizzaProdotto();
            selezionaToggleButton(SitoAbbigliamentoSportivo.group1,info[8]);
            selezionaToggleButton(SitoAbbigliamentoSportivo.group2,info[9]);
            String i10=info[10];
            if(!i10.equals(""))
              SitoAbbigliamentoSportivo.quantityTextField.setText(info[10]);
            String i11=info[11];
            if(!i11.equals(""))
              SitoAbbigliamentoSportivo.messaggioAggiungiCarrello.setText(info[11]);
           } 
          }
    }
}


/*
Note:
(1) La classe nella sua globalità tramite la salvaStato salva lo scenario(o stato)corrente
    salvando nel primo elemento dell'array di stringhe il nome dello scenario, mentre 
    tramite la carica stato controlla prima il valore del primo elemento così da sapere 
    a che scenario faranno riferimento le informazioni a seguire e poi estrapola le 
    informazioni per ripristinare l'ultimo stato salvato.
    Il valore di nomescenario cambia di volta in volta garantendo in ogni istante che se 
    l'applicazione venisse chiusa vengano salvate le informazioni corrette per quello 
    scenario.
*/