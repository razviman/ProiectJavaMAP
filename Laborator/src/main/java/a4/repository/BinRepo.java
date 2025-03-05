package a4.repository;
import a4.domain.*;
import java.io.*;
import java.util.ArrayList;
import a4.repository.*;

public class BinRepo <T extends entity> extends AbstractFileRepo<T> {

    public BinRepo(String fileName) {
        super(fileName);
        try {
            LoadFromFile();
        } catch (RepoException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void SaveToFile() throws RepoException {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.fileName))) {
            oos.writeObject(list);
        } catch (IOException e) {
            throw new RepoException(e.getMessage(), e);
        }

    }

    @Override
    protected void LoadFromFile() throws RepoException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.fileName))){
            this.list = (ArrayList<T>) ois.readObject();
        } catch (FileNotFoundException exc) {
            System.out.println("Fisierul a fost initializat!");
        } catch (IOException | ClassNotFoundException exc) {
            throw new RepoException("Eroare la incarcarea fisierului", exc);
        }
    }
}