package a4.repository;
import a4.domain.*;
import org.sqlite.SQLiteDataSource;
import com.github.javafaker.Faker;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SqlRepoTort extends Repository<tort> {
    protected String URL;
    protected Connection conn = null;

    public SqlRepoTort(String URL) throws Exception {
        this.URL = URL;
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
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS tort(id int, tip varchar(200));");
            }
        } catch (SQLException e) {
            System.err.println("Eroare in createBase : " + e.getMessage());
        }
    }

    protected void initTables() {


        List<String> tipTort = Arrays.asList("Nunta", "Alina", "Bianca", "Diplomat", "Emoke", "Morena", "Octavia", "Jeni");
        Random rand = new Random();

        try {
            try (PreparedStatement statement1 = conn.prepareStatement("SELECT COUNT(*) from tort")){
                statement1.executeQuery().next();
                if (statement1.executeQuery().getInt(1) == 0)
                    for (int i = 0; i < 50; i++) {
                        try (PreparedStatement statement = conn.prepareStatement("INSERT INTO tort VALUES(?, ?)")) {
                            statement.setInt(1, i);
                            statement.setString(2, tipTort.get(rand.nextInt(tipTort.size())));
                            statement.executeUpdate();
                        }
                    }
            }
        }catch (SQLException e) {
            System.err.println("[ERROR] insert : " + e.getMessage());
        }
    }


    protected void initRepo() throws Exception {
        list.clear();
        ArrayList<tort> torturi = getLista();
        list.addAll(torturi);
    }

    @Override
    public void Add(tort t) throws RepoException{
        super.Add(t);
        try {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO tort VALUES(?, ?)")) {
                statement.setInt(1, t.getId());
                statement.setString(2, t.getTip());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] insert : " + e.getMessage());
        }
    }

    @Override
    public void removeById(int id) throws RepoException {
        super.removeById(id);
        try {
            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM tort WHERE id=?")) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] delete : " + e.getMessage());
        }
    }

    @Override
    public void Update(int id, tort t) throws RepoException {
        removeById(id);
        Add(t);



    }





    public ArrayList<tort> getLista() {
        ArrayList<tort> torturi = new ArrayList<>();
        try {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * from tort"); ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    tort p = new tort(rs.getInt("id"), rs.getString("tip"));
                    torturi.add(p);
                }
            }
        } catch (SQLException ex) {
            System.err.println("[ERROR] getList : " + ex.getMessage());
        }
        return torturi;
    }}
