
import javafx.beans.property.*;

public class MisureTaglie {
    
    private final SimpleStringProperty taglia;
    private final SimpleIntegerProperty misura1;
    private final SimpleIntegerProperty misura2;
    private final SimpleIntegerProperty misura3;
    
    public MisureTaglie(String tgl,int m1,int m2,int m3){
        this.taglia=new SimpleStringProperty(tgl);
        this.misura1=new SimpleIntegerProperty(m1);
        this.misura2=new SimpleIntegerProperty(m2);
        this.misura3=new SimpleIntegerProperty(m3);    
    }
    
    public String getTaglia(){return taglia.get();}
    public int getMisura1(){return misura1.get();}
    public int getMisura2(){return misura2.get();}
    public int getMisura3(){return misura3.get();}
        
}