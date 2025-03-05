package a4.repository;

import a4.domain.*;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlRepoTortsiComanda {
    protected String URL;
    protected Connection conn = null;

    public SqlRepoTortsiComanda(String URL) throws Exception {
        this.URL = URL;
        openConnection();

        createBase();
        initDataFromComenzi();
    }

    public void initDataFromComenzi() {

        try {
            String checkQuery = "SELECT COUNT(*) FROM tort_comanda";
            PreparedStatement checkStatement = conn.prepareStatement(checkQuery); ResultSet checkRs = checkStatement.executeQuery(); {
                int count = checkRs.getInt(1);
                if (count > 0)
                    return;
            // Selectăm toate comenzile și torturile asociate din tabela comenzi
            String query = "SELECT id AS id_comanda, torturi FROM comenzi";
            try (PreparedStatement statement = conn.prepareStatement(query); ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    int idComanda = rs.getInt("id_comanda");
                    String torturiRaw = rs.getString("torturi"); // Lista de ID-uri de torturi (e.g., "1,2,3")

                    if (torturiRaw != null && !torturiRaw.isEmpty()) {
                        // Despărțim lista de torturi și creăm relații
                        String[] tortIds = torturiRaw.split(",");
                        for (String tortId : tortIds) {
                            addRelation(Integer.parseInt(tortId.trim()), idComanda); // Adăugăm fiecare relație
                        }
                    }
                }
            }}
        } catch (SQLException e) {
            System.err.println("[ERROR] initDataFromComenzi : " + e.getMessage());
        }
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

    protected void createBase() {
        try {
            try (final Statement stmt = conn.createStatement()) {
                // Creează tabelul pentru relația many-to-many
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS tort_comanda (" +
                        "id_tort INT, " +
                        "id_comanda INT, " +
                        "PRIMARY KEY (id_tort, id_comanda)," +
                        "FOREIGN KEY (id_tort) REFERENCES tort(id)," +
                        "FOREIGN KEY (id_comanda) REFERENCES comanda(id));");
            }
        } catch (SQLException e) {
            System.err.println("Eroare in createBase : " + e.getMessage());
        }
    }

    public void addRelation(int idTort, int idComanda) {
        try {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO tort_comanda (id_tort, id_comanda) VALUES (?, ?)")) {
                statement.setInt(1, idTort);
                statement.setInt(2, idComanda);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] addRelation : " + e.getMessage());
        }
    }

    public void removeRelation(int idComanda) {
        try {
            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM tort_comanda WHERE  id_comanda = ?")) {

                statement.setInt(1, idComanda);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] removeRelation : " + e.getMessage());
        }
    }

}