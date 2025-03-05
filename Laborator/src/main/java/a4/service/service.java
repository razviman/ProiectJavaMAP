package a4.service;
import a4.repository.RepoException;
import a4.repository.Repository;
import a4.domain.tort;
import a4.domain.comanda;

import java.util.*;
import java.util.stream.Collectors;

public class service {

    public Repository<tort> repoTort;
    public Repository<comanda> repoComanda;


    public service(Repository repo1, Repository repo2) {
    this.repoTort = repo1;
    this.repoComanda = repo2;
}

    public boolean Addtort(String den) {
        try {
            tort tort_adaugare = new tort(den);
            repoTort.Add(tort_adaugare);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public ArrayList<tort> getTorturi() {
        return repoTort.getList();
    }

    public boolean FindByIdTort(int id) {
         return repoTort.findById(id);
    }

    public boolean FindByIdComanda(int id) {
        return repoComanda.findById(id);
    }

    public void AddTort_to_Comanda(int id_comanda, int id_tort) throws Exception {
        comanda comanda = repoComanda.getElementById(id_comanda);
        tort tort = repoTort.getElementById(id_tort);
        comanda.AddTort(tort);
        repoComanda.Update(id_comanda, comanda);
    }

    public void UpdateData(int id, String date) throws Exception {
        comanda c = repoComanda.getElementById(id);
        comanda c2 = new comanda(c.getId(), c.getTorturi(), date);
        repoComanda.Update(id, c2);


    }

    public void DeleteComanda(int id) throws Exception {
        repoComanda.removeById(id);
    }

    public void UpdateTort(int id, String den) throws RepoException {
       tort t = repoTort.getElementById(id);
       t.setTip(den);
       repoTort.Update(id, t);


    }

    public void DeleteTort(int id) throws Exception {
        repoTort.removeById(id);
    }

    public boolean AddComanda(int id, String data) {
        try { ArrayList<tort> lista = new ArrayList<tort>();
            lista.add(repoTort.getElementById(id));
            comanda comanda_noua = new comanda(lista, data);
            repoComanda.Add(comanda_noua);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public ArrayList<comanda> getComenzi() {
        return repoComanda.getList();
    }

    public tort getLastTort() throws Exception {
        return repoTort.getLast();
    }



        public List<Date> getZileComenzi() {
            List<comanda> comenzi = repoComanda.getList();
            return comenzi.stream()
                    .collect(Collectors.groupingBy(
                            comanda::getData,
                            Collectors.summingInt(comanda::GetNrTorturi)
                    ))
                    .entrySet().stream()
                    .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }


    public int getNrTorturiInZi(Date zi) {
        List<comanda> comenzi = repoComanda.getList();
        return comenzi.stream()
                .filter(comanda -> comanda.getData().equals(zi))
                .mapToInt(comanda::GetNrTorturi)
                .sum();
    }

    public Map<Integer, Integer> getTorturiPeLuna() {
        List<comanda> comenzi = repoComanda.getList();
        return comenzi.stream()
                .collect(Collectors.groupingBy(
                        comanda -> comanda.getData().getMonth() + 1,
                        Collectors.summingInt(comanda::GetNrTorturi)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }


    public Map<tort, Integer> getTorturiComandate() {
        return repoComanda.getList().stream()
                .flatMap(comanda -> comanda.getTorturi().stream())
                .collect(Collectors.groupingBy(
                        tort -> tort,
                        Collectors.summingInt(tort -> 1)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }


}
