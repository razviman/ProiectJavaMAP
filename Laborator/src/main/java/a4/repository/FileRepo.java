package a4.repository;
import a4.domain.*;

import java.io.*;

public class FileRepo<T extends entity> extends AbstractFileRepo<T> {

    protected converter<T> converter;

    public FileRepo(String fileName, converter<T> converter) {
        super(fileName);
        this.converter = converter;
        try {
            LoadFromFile();

        }catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void SaveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.fileName))) {
            for (var entity : this.list) {
                String asString = converter.toString((T) entity);
                bw.write(asString);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file");
        }
    }

    @Override
    protected void LoadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            String line = br.readLine();
            while (line != null) {
                list.add(converter.toEntity(line));
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul de date este gol");
        } catch (IOException e) {
            System.out.println("Error loading file");
        }
    }
}
