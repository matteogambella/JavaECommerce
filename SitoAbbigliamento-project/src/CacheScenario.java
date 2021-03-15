
import java.io.*;


public class CacheScenario{

    private String nomeFileCache;
    
    public CacheScenario(String nomefile) {
        this.nomeFileCache = nomefile;
    }
    
    public String[] leggi() { // (1)
        String[] info={""};
        try (FileInputStream fis = new FileInputStream(this.nomeFileCache);
             ObjectInputStream oin = new ObjectInputStream(fis);) {
            info = (String[]) oin.readObject();
        } catch (ClassNotFoundException ex) {
            System.out.println("Contenuto cache scenario non valido");
        } catch (IOException ex) {
            System.out.println("Errore lettura cache scenario");
        }
        return info;
    }
    
    public boolean scrivi(String[] info) { // (2)
        boolean success = false;
        try (FileOutputStream fout = new FileOutputStream(this.nomeFileCache);
             ObjectOutputStream oout = new ObjectOutputStream(fout);) {
            oout.writeObject(info);
            success = true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return success;
    }
    
}

/*
Note:
(1): il metodo leggi consente di leggere un' array di stringhe dal file binario cache
(2): il metodo scrivi consente di scrivere un' array di stringhe nel file binario cache
*/
