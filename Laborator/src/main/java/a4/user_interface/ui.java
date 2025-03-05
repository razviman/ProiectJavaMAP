package a4.user_interface;
import a4.service.service;
import a4.domain.tort;
import a4.domain.comanda;

import java.util.*;


public class ui {
    public service service;

    public ui(service service) {
        this.service = service;

    }

    public void afisare_meniu () {
        System.out.println("MENIU");
        System.out.println("1.Adaugare tort");
        System.out.println("2.Afisare torturi");
        System.out.println("3.Actualizare denumire tort");
        System.out.println("4.Stergere tort");
        System.out.println("5.Adaugare comanda");
        System.out.println("6.Afisare comenzi");
        System.out.println("7.Adaugare tort comanda");
        System.out.println("8.Modifica data comanda");
        System.out.println("9.Stergere comanda");
        System.out.println("10.Numarul de torturi comandate in fiecare zi");
        System.out.println("11.Numarul de torturi comandate in fiecare luna");
        System.out.println("12.Afisare cele mai des comandate torturi");
        System.out.println("0.Iesire");


    }

    public void meniu () {
        boolean action = true;
        while (action) {
            afisare_meniu();
            Scanner sc = new Scanner(System.in);
            int option = sc.nextInt();
            if (option == 1) {
                System.out.println("Introduceti denumirea tortului");
                String denumirea = sc.next();
                service.Addtort(denumirea);
            }
            else if (option == 2) {
                ArrayList<tort> lista;
                lista = service.getTorturi();
                for ( tort t : lista ) {
                    System.out.println(t);
                }}

            else if (option == 3) {
                try { int id;
                System.out.println("Introduceti id-ul tortului:");
                id =sc.nextInt();
                if(!service.FindByIdTort(id)) throw new Exception();
                System.out.println("Introduceti denumire tortului");
                String denumire = sc.next();
                service.UpdateTort(id, denumire);
                } catch (Exception e) {
                    System.out.println("Id inexistent");
                }
            }

            else if (option == 4) {
                try {
                    System.out.println("Introduceti id-ul tortului");
                    int id = sc.nextInt();
                    service.DeleteTort(id);
                }catch (Exception e) {
                    System.out.println("Id inexistent");
                }
            }
            else if (option == 5) {
                try {
                    System.out.println("Introduceti id-ul tortului pe care doriti sa-l comandati");
                    int id = sc.nextInt();
                    if(!service.FindByIdTort(id)) throw new Exception();
                    System.out.println("Introduceti ziua");
                    int zi = sc.nextInt();
                    System.out.println("Introduceti luna");
                    int luna = sc.nextInt();
                    System.out.println("Introduceti anul");
                    int an = sc.nextInt();
                    if (zi<0 || zi>31 ) throw new Exception();
                    else if (luna<0 || luna>12 ) throw new Exception();
                    else if (an<2024 || an>2100 ) throw new Exception();
                    String data;
                    data = luna + "-" + zi + "-" + an;
                    service.AddComanda(id, data );
                } catch (Exception e) {
                    System.out.println("Date invalide");
                }

            }
            else if (option == 6) {
                ArrayList<comanda> lista;
                lista = service.getComenzi();
                for ( comanda t : lista ) {
                    System.out.println(t);
                }
            }

            else if (option == 7) {
                try {
                    System.out.println("Introduceti id-ul comenzii");
                    int id_comanda = sc.nextInt();
                    if (!service.FindByIdComanda(id_comanda)) throw new Exception();
                    System.out.println("Introduceti id-ul tortului pe care doriti sa il adaugati la comanda");
                    int id = sc.nextInt();
                    if (!service.FindByIdTort(id)) throw new Exception();
                    service.AddTort_to_Comanda(id_comanda, id);


                }catch (Exception e) {
                    System.out.println("Id inexistent");
                }
            }

            else if (option == 8) {
                try {
                    System.out.println("Introduceti id-ul comenzii");
                    int id_comanda = sc.nextInt();
                    if (!service.FindByIdComanda(id_comanda)) throw new Exception();
                    System.out.println("Introduceti ziua");
                    int zi = sc.nextInt();
                    System.out.println("Introduceti luna");
                    int luna = sc.nextInt();
                    System.out.println("Introduceti anul");
                    int an = sc.nextInt();
                    if (zi<0 || zi>31 ) throw new Exception();
                    else if (luna<0 || luna>12 ) throw new Exception();
                    else if (an<2024 || an>2100 ) throw new Exception();
                    String data;
                    data = luna + "-" + zi + "-" + an;
                    service.UpdateData(id_comanda, data);

                }catch (Exception e) {
                    System.out.println("Id inexistent");
            }}

            else if(option==9) {
                try {
                    System.out.println("Introduceti id-ul comenzii");
                    int id_comanda = sc.nextInt();
                    if (!service.FindByIdComanda(id_comanda)) throw new Exception();
                    service.DeleteComanda(id_comanda);
                } catch (Exception e) {
                    System.out.println("Id inexistent");
                }
            }
            else if(option==10) {
                List<Date> lista = service.getZileComenzi();
                for (Date t : lista ) {
                    System.out.println("Data: "+t.toString()+", Numarul de torturi: "+ service.getNrTorturiInZi(t));
                }
            }
            else if (option==11) {
                Map<Integer, Integer> lista = service.getTorturiPeLuna();
                for (Map.Entry<Integer, Integer> entry : lista.entrySet()) {
                    Integer luna = entry.getKey(); // Cheia (luna)
                    Integer nrTorturi = entry.getValue(); // Valoarea (numărul de torturi)
                    System.out.println("Luna: " + luna + ", Torturi comandate: " + nrTorturi);
                }

            }
            else if (option==12) {
            Map<tort, Integer> lista = service.getTorturiComandate();
                for (Map.Entry<tort, Integer> entry : lista.entrySet()) {
                    tort t = entry.getKey(); // Cheia (luna)
                    Integer nrTorturi = entry.getValue(); // Valoarea (numărul de torturi)
                    System.out.println("Luna: " + t + ", Torturi comandate: " + nrTorturi);
                }
            }

            else if (option == 0) {
                action = false;
                System.out.println("Bye");
            }
        }
    }
}
