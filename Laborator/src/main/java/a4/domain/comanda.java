package a4.domain;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class comanda extends entity {
    ArrayList<tort> torturi;
    Date data;


    public comanda(ArrayList<tort> torturi, Date data) {
        super();
        this.torturi = torturi;
        this.data = data;
    }

    public comanda(ArrayList<tort> torturi, String data) {
        super();
        this.torturi = torturi;

        SimpleDateFormat formatData = new SimpleDateFormat("MM-dd-yyyy");
        try {
            this.data = formatData.parse(data);  // Setăm campul `data` cu data parsata
        } catch (ParseException e) {
            System.out.println("Eroare la parsarea datei: " + e.getMessage());
        }
    }

    public comanda(int id1, ArrayList<tort> torturi, String data) {
        id = id1;
        this.torturi = torturi;

        SimpleDateFormat formatData = new SimpleDateFormat("MM-dd-yyyy");
        try {
            this.data = formatData.parse(data);  // Setăm campul `data` cu data parsata
        } catch (ParseException e) {
            System.out.println("Eroare la parsarea datei: " + e.getMessage());
        }
    }

    public ArrayList<tort> getTorturi() {
        return torturi;
    }


    public Date getData() {
        return data;
    }

    public int GetNrTorturi() {
        return torturi.size();
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDataString() {
        SimpleDateFormat formatData = new SimpleDateFormat("MM-dd-yyyy");
        return formatData.format(data);
    }


    public void AddTort(tort tort) {
        torturi.add(tort);
    }

    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

        return ( "Comanda: { id=" + id +  ", data_livrare=" + formatter.format(data)  + ", lista_torturi=" +torturi.toString() + "}");
    }

    public String getIds() {
        String ids= null;
        for(tort tort : torturi) {
            ids = String.valueOf(tort.getId()) + ", ";

        }
        ids = ids.substring(0, ids.length()-2);
    return ids;
    }

}


