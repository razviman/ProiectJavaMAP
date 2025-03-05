package a4;

import a4.domain.ID_generator;
import a4.domain.comanda;
import a4.service.*;
import a4.domain.*;

import a4.user_interface.ui;
import a4.service.*;
import a4.config.properties;


import a4.repository.*;

import java.util.ArrayList;


public class main {
    public static Repository<tort> repTort;
    public static Repository<comanda> repComanda;
    public static service service;
    public static ui ui;
    public static void main(String[] args) throws Exception {
        service = new service(repTort, repComanda);

//        Cofetarie.launch(Cofetarie.class, args);

        try {



            properties prop = new properties("src/main/java/a4/data/settings.properties");
            switch (prop.getRepositoryType()) {
                case "binary":
                    repTort = new BinRepo<tort>(prop.getTortFile());
                    repComanda = new BinRepo<comanda>(prop.getComandaFile());
                    break;
                case "text":
                    converterTort convTort = new converterTort();
                    repTort = new FileRepo<tort>(prop.getTortFile(), convTort);
                    converterComanda convComanda = new converterComanda();
                    repComanda= new FileRepo<comanda>(prop.getComandaFile(), convComanda);
                    break;
                case "sql":
                    repTort = new SqlRepoTort(prop.getURL());
                    ArrayList<tort> produse = repTort.getList();
                    repComanda = new SqlRepoComanda(prop.getURL(), produse);
            //        SqlRepoTortsiComanda REPO = new SqlRepoTortsiComanda(prop.getURL());
                    break;
                default:
                    throw new Exception("Invalid repository type!");
            }

            service = new service(repTort, repComanda);
            ui = new ui(service);
            if(prop.getTipRulare().equals("interfata vizuala")){
                Cofetarie.launch(Cofetarie.class, args);
            }else{
                if(prop.getTipRulare().equals("linie de comanda")){
                    ui.meniu();
                }
                else
                    throw new Exception("Invalid run type!");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }}