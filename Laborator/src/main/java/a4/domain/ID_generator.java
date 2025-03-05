package a4.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ID_generator {
    int id_gen = 0;
    public ID_generator () {
        id_gen = ID_gen(id_gen);
    }

    public int get_id() {
        return id_gen;
    }

  public int ID_gen(int id) {
    String caleFisier = "C:\\Users\\Razvan\\IdeaProjects\\Laborator\\src\\main\\java\\a4\\data\\ID_GEN";
    int id1 = 0;


    try (BufferedReader br = new BufferedReader(new FileReader(caleFisier))) {
        String linie = br.readLine();
        if (linie != null) {
            id1 = Integer.parseInt(linie);
            id = id1;
        }
    } catch (IOException | NumberFormatException e) {
        System.out.println("Eroare in fisierul cu ID");
    }

    id1++;

    try (FileWriter fw = new FileWriter(caleFisier)) {
        fw.write(Integer.toString(id1));
    } catch (IOException e) {
        System.out.println("Eroare la scrierea in fisier");
    }
    return id;
 }



}


