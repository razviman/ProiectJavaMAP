package a4.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class converterComanda extends converter<comanda> {

    @Override
    public String toString(comanda elem) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        return elem.getId() +  ";" + formatter.format(elem.getData())  + ";" +elem.getTorturi().toString();
    }

    @Override
    public comanda toEntity(String elem) {
        String[] tokens = elem.split(";");
        String data = tokens[1];
        ArrayList<tort> lista = new ArrayList<>();
        String[] separator = tokens[2].split(",");
        int ok=1;
        while(separator.length>ok) {
            try {
                lista.add(new tort(separator[ok]));
                ok+=2;


            } catch (Exception e) {
                System.out.println("Eroare la parsare");
            }


        }

        return new comanda(lista, data);
    }
}
