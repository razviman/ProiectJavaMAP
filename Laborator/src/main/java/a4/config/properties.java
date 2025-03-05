package a4.config;

import a4.repository.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class properties {
    private final Map<String, String> properties = new HashMap<>();

    public properties(String filepath) throws RepoException {
        loadProperties(filepath);
    }

    private void loadProperties(String filepath) throws RepoException {
        try (FileInputStream fis = new FileInputStream(filepath)) {
            java.util.Properties prop = new java.util.Properties();
            prop.load(fis);

            for (String key : prop.stringPropertyNames()) {
                properties.put(key, prop.getProperty(key).replace("\"", "").trim());
            }
        } catch (IOException e) {
            throw new RepoException("Eroare la citirea fișierului de proprietăți: " + filepath, e);
        }
    }

    public String getRepositoryType() throws RepoException {
        String repoType = properties.get("Repository");
        if (repoType == null) {
            throw new RepoException("Lipsește tipul de repository în fișierul de proprietăți.");
        }
        return repoType;
    }

    public String getComandaFile() throws RepoException {
        String comandaFile = properties.get("ComandaFile");
        if (comandaFile == null) {
            throw new RepoException("Lipsește fișierul pentru comenzi în fișierul de proprietăți.");
        }
        return comandaFile;
    }

    public String getTortFile() throws RepoException {
        String tortFile = properties.get("TortFile");
        if (tortFile == null) {
            throw new RepoException("Lipsește fișierul pentru torturi în fișierul de proprietăți.");
        }
        return tortFile;
    }

    public String getURL() throws RepoException {
        String url = properties.get("URL");
        if (url == null) {
            throw new RepoException("Lipseste adresa url in fisierul de proprietati.");
        }
        return url;
    }

    public String getTipRulare() throws RepoException {
        String tiprulare = properties.get("TipRulare");
        if (tiprulare == null) {
            throw new RepoException("Lipseste adresa url in fisierul de proprietati.");
        }
        return tiprulare;
    }
}