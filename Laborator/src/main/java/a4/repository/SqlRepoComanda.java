package a4.repository;

import com.github.javafaker.Faker;
import a4.domain.ID_generator;
import a4.domain.comanda;
import org.sqlite.SQLiteDataSource;
import a4.domain.tort;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SqlRepoComanda extends Repository<comanda>{
    protected String URL;
    protected Connection conn = null;
    protected ArrayList<tort> torturi;
//    private SqlRepoTortsiComanda REPO;

    public SqlRepoComanda(String URL, ArrayList<tort> torturi) throws Exception{
        this.URL = URL;
        this.torturi = torturi;
//        REPO = new SqlRepoTortsiComanda(URL);
        openConnection();
        createBase();
        initTables();
        initRepo();
    }

    private void openConnection() {
        try {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(URL);
            if (conn == null || conn.isClosed())
                conn = ds.getConnection();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    private void closeConnection() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    protected void createBase() {
        try {
            try (final Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS comenzi(id int, data_liv varchar(20), torturi varchar(20));");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] createBase : " + e.getMessage());
        }
    }


    protected void initTables() {
        Faker faker = new Faker();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

        try {
            try (PreparedStatement statement1 = conn.prepareStatement("SELECT COUNT(*) from comenzi")){
               statement1.executeQuery().next() ;

                if (statement1.executeQuery().getInt(1) == 0){
                for (int i = 0; i < 50; i++) {
                        try (PreparedStatement statement = conn.prepareStatement("INSERT INTO comenzi VALUES (?, ?, ?)")) {
                            statement.setInt(1, i);
                            Date data = faker.date().future(1000, java.util.concurrent.TimeUnit.DAYS);
                            statement.setString(2, formatter.format(data));
                            String IDs = torturi.getFirst().getId() + "," + torturi.getLast().getId();
                            statement.setString(3, IDs);
                            statement.executeUpdate();
                        }
                    }}
            }
        }catch (SQLException e) {
            System.err.println("[ERROR] insert : " + e.getMessage());
        }

    }

    protected void initRepo() throws Exception {
        list.clear();
        ArrayList<comanda> com = getLista();
        list.addAll(com);
    }

    @Override
    public void Add(comanda c) throws RepoException{
        super.Add(c);
        try {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO comenzi VALUES(?, ?, ?)")) {
                statement.setInt(1, c.getId());
                statement.setString(2, c.getDataString());
                ArrayList<tort> p = c.getTorturi();
                String IDs = new String();
                for(tort t : p) {
                    IDs = IDs + t.getId() + ",";
//                    REPO.addRelation(c.getId(), t.getId());
                }
                IDs = IDs.substring(0, IDs.length() - 1);
                statement.setString(3, IDs);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    @Override
    public void removeById(int id) throws RepoException{
        super.removeById(id);
        try {
            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM comenzi WHERE id=?")) {
                statement.setInt(1, id);
                statement.executeUpdate();
//                REPO.removeRelation(id);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }


    public ArrayList<comanda> getLista() {
        ArrayList<comanda> comenzi = new ArrayList<>();

        try {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * from comenzi"); ResultSet rs = statement.executeQuery();) {
                while (rs.next()) {
                    ArrayList<tort> t = new ArrayList<>();
                    String[] IDs = rs.getString("torturi").split(",");

                    for(tort tort: torturi) {
                        for(String id: IDs){
                            if(tort.getId() == Integer.parseInt(id)){
                                t.add(tort);
                            }

                        }
                    }

                    comanda c = new comanda(rs.getInt("id"), t, rs.getString("data_liv"));
                    comenzi.add(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return comenzi;
    }
    @Override
    public void Update(int id, comanda t) throws RepoException {
        removeById(id);
        Add(t);



    }
}


