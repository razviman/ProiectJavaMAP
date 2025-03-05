package a4;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.ParseException;
import java.util.*;

import a4.service.service;
import a4.domain.*;



public class Cofetarie extends Application{

    private service service = main.service;

    @Override
    public void start(Stage primaryStage) {
        VBox torturiComenziBox = new VBox(10);
        torturiComenziBox.setPadding(new Insets(10));

        Label titleLabel = new Label("Gestionare Torturi si Comenzi");


        Button addTortButton = new Button("Adaugă Tort");
        Button displayTortButton = new Button("Afișează Torturi");
        Button deleteTortButton = new Button("Șterge Tort");
        Button updateTortButton = new Button("Actualizare Tort");

        addTortButton.setOnAction(e -> showAddTortDialog());
        displayTortButton.setOnAction(e -> showTortList());
        deleteTortButton.setOnAction(e -> showDeleteTortDialog());
        updateTortButton.setOnAction(e -> showUpdateTortDialog());


        Button addComButton = new Button("Adaugă Comandă");
        Button displayComButton = new Button("Afișează Comenzi");
        Button deleteComButton = new Button("Șterge Comandă");
        Button updateComButton = new Button("Adaugă Tort în Comandă");
        Button updateCom2Button = new Button("Modifică Data Comandă");

        addComButton.setOnAction(e -> showAddComDialog());
        displayComButton.setOnAction(e -> showComList());
        deleteComButton.setOnAction(e -> showDeleteComDialog());
        updateComButton.setOnAction(e -> showUpdateComDialog());
        updateCom2Button.setOnAction(e -> showUpdateCom2Dialog());

        torturiComenziBox.getChildren().addAll(
                titleLabel,
                new Label("Torturi:"), addTortButton, displayTortButton, deleteTortButton, updateTortButton,
                new Label("Comenzi:"), addComButton, displayComButton, deleteComButton, updateComButton, updateCom2Button
        );


        VBox rapoarteBox = new VBox(10);
        rapoarteBox.setPadding(new Insets(100, 0, 30, 0));

        Button nrTorturiPeZiButton = new Button("Numarul de torturi comandate in fiecare zi");
        Button nrTorturiPeLunaButton = new Button("Numarul de torturi comandate in fiecare luna");
        Button celeMaiVandute = new Button("Afisare cele mai des comandate torturi");

        nrTorturiPeZiButton.setOnAction(e -> showNrTorturiPeZi());
        nrTorturiPeLunaButton.setOnAction(e -> showNrTorturiPeLuna());
        celeMaiVandute.setOnAction(e -> showCeleMaiVandute());

        rapoarteBox.getChildren().addAll(
                new Label("Rapoarte:"), nrTorturiPeZiButton, nrTorturiPeLunaButton, celeMaiVandute
        );

        // Structura principală: două coloane
        HBox root = new HBox(20);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(torturiComenziBox, rapoarteBox);

        Scene scene = new Scene(root, 600, 450);
        primaryStage.setTitle("Gestionare Torturi și Comenzi");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAddTortDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Adaugă Tort");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));


        TextField tipField = new TextField();
        tipField.setPromptText("Tip");


        Button saveButton = new Button("Salvează");
        saveButton.setOnAction(e -> {
            try {

                String tip = tipField.getText();


                service.Addtort(tip);
                dialog.close();
            } catch (Exception ex) {
                showErrorDialog("Eroare la adăugare produs: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(tipField, saveButton);

        Scene scene = new Scene(layout, 300, 200);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showTortList() {
        Stage listStage = new Stage();
        listStage.setTitle("Lista Torturi");

        TableView<tort> tableView = new TableView<>();

        TableColumn<tort, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));

        TableColumn<tort, String> tipColumn = new TableColumn<>("Tip");
        tipColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getTip()));

        tableView.getColumns().addAll(idColumn, tipColumn);

        try {
            tableView.getItems().addAll(service.getTorturi());
        } catch (Exception ex) {
            showErrorDialog("Eroare la încărcarea produselor: " + ex.getMessage());
        }

        VBox layout = new VBox(tableView);
        Scene scene = new Scene(layout, 400, 300);

        listStage.setScene(scene);
        listStage.show();
    }

    private void showDeleteTortDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Ștergere Tort");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField idField = new TextField();
        idField.setPromptText("ID");

        Button deleteButton = new Button("Șterge");
        deleteButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                if(!service.FindByIdTort(id)) throw new Exception("Id inexistent");
                service.DeleteTort(id);
                dialog.close();
            } catch (Exception ex) {
                showErrorDialog("Eroare la ștergerea produsului: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(idField, deleteButton);

        Scene scene = new Scene(layout, 300, 150);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showUpdateTortDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Actualizare tort");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField idField = new TextField();
        idField.setPromptText("ID");
        TextField tipField = new TextField();
        tipField.setPromptText("Tip");


        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String tip = tipField.getText();
                if(!service.FindByIdTort(id)) throw new Exception("Id inexistent");
                service.UpdateTort(id, tip);
                dialog.close();
            } catch (Exception ex) {
                showErrorDialog("Eroare la actualizarea produsului: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(idField, tipField, updateButton);

        Scene scene = new Scene(layout, 300, 150);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showAddComDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Adaugă Comanda");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField idField = new TextField();
        idField.setPromptText("id");

        TextField ziuaField = new TextField();
        ziuaField.setPromptText("Zi");

        TextField lunaField = new TextField();
        lunaField.setPromptText("Luna");

        TextField anField = new TextField();
        anField.setPromptText("An");


        Button saveButton = new Button("Salvează");
        saveButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                int ziua = Integer.parseInt(ziuaField.getText());
                int luna = Integer.parseInt(lunaField.getText());
                int an = Integer.parseInt(anField.getText());
                if(!service.FindByIdTort(id)) throw new Exception("Id inexistent");
                if (ziua<0 || ziua>31 ) throw new Exception("Data invalida");
                else if (luna<0 || luna>12 ) throw new Exception("Data invalida");
                else if (an<2024 || an>2100 ) throw new Exception("Data invalida");
                String data = luna + "-" + ziua + "-" + an;

                service.AddComanda(id, data);
                dialog.close();
            } catch (Exception ex) {
                showErrorDialog("Eroare la adăugare comanda: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(idField, ziuaField, lunaField, anField, saveButton);

        Scene scene = new Scene(layout, 300, 200);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showComList() {
        Stage listStage = new Stage();
        listStage.setTitle("Lista Comenzi");

        TableView<comanda> tableView = new TableView<>();

        TableColumn<comanda, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));

        TableColumn<comanda, String> dataColumn = new TableColumn<>("Data");
        dataColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDataString()));

        TableColumn<comanda, String> torturiColumn = new TableColumn<>("Torturi");
        torturiColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTorturi()).asString());

        tableView.getColumns().addAll(idColumn, dataColumn, torturiColumn);

        try {
            tableView.getItems().addAll(service.getComenzi());
        } catch (Exception ex) {
            showErrorDialog("Eroare la încărcarea produselor: " + ex.getMessage());
        }

        VBox layout = new VBox(tableView);
        Scene scene = new Scene(layout, 400, 300);

        listStage.setScene(scene);
        listStage.show();
    }

    private void showDeleteComDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Ștergere Comanda");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField idField = new TextField();
        idField.setPromptText("ID");

        Button deleteButton = new Button("Șterge");
        deleteButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                if (!service.FindByIdComanda(id)) throw new Exception("Id inexistent");
                service.DeleteComanda(id);
                dialog.close();
            } catch (Exception ex) {
                showErrorDialog("Eroare la ștergerea comenzii: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(idField, deleteButton);

        Scene scene = new Scene(layout, 300, 150);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showUpdateComDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Adaugare tort in comanda");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField idField = new TextField();
        idField.setPromptText("ID comanda");
        TextField id2Field = new TextField();
        id2Field.setPromptText("Id tort");


        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
               int id2 = Integer.parseInt(id2Field.getText());
                if (!service.FindByIdComanda(id)) throw new Exception("Id comanda inexistent");
                if (!service.FindByIdTort(id2)) throw new Exception("Id tort inexistent");
                service.AddTort_to_Comanda(id, id2);
                dialog.close();
            } catch (Exception ex) {
                showErrorDialog("Eroare la ștergerea produsului: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(idField, id2Field, updateButton);

        Scene scene = new Scene(layout, 300, 150);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showUpdateCom2Dialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Modificare data comanda");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField idField = new TextField();
        idField.setPromptText("ID comanda");
        TextField ziuaField = new TextField();
        ziuaField.setPromptText("Zi");

        TextField lunaField = new TextField();
        lunaField.setPromptText("Luna");

        TextField anField = new TextField();
        anField.setPromptText("An");


        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                int ziua = Integer.parseInt(ziuaField.getText());
                int luna = Integer.parseInt(lunaField.getText());
                int an = Integer.parseInt(anField.getText());
                if(!service.FindByIdComanda(id)) throw new Exception("Id inexistent");
                if (ziua<0 || ziua>31 ) throw new Exception("Data invalida");
                else if (luna<0 || luna>12 ) throw new Exception("Data invalida");
                else if (an<2024 || an>2100 ) throw new Exception("Data invalida");
                String data = ziua + "-" + luna + "-" + an;
                service.UpdateData(id, data);
                dialog.close();
            } catch (Exception ex) {
                showErrorDialog("Eroare la ștergerea produsului: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(idField,ziuaField,lunaField,anField, updateButton);

        Scene scene = new Scene(layout, 300, 300);
        dialog.setScene(scene);
        dialog.show();
    }
    private void showNrTorturiPeZi() {
        Stage nrTortStage = new Stage();
        nrTortStage.setTitle("Numarul torturilor vandute pe zile");

        VBox layout = new VBox();
        layout.setSpacing(10);

        try {
            List<Date> zile = service.getZileComenzi();
            for (Date zi : zile) {
                int nrtorturi = service.getNrTorturiInZi(zi);
                Label label = new Label(zi + ": " + nrtorturi);
                layout.getChildren().add(label);
            }
        } catch (Exception ex) {
            Label errorLabel = new Label("Eroare la încărcarea datelor: " + ex.getMessage());
            layout.getChildren().add(errorLabel);
        }

        Scene scene = new Scene(layout, 300, 500);

        nrTortStage.setScene(scene);
        nrTortStage.show();
    }

    private void showNrTorturiPeLuna() {
        Stage nrTortStage = new Stage();
        nrTortStage.setTitle("Numarul torturilor vandute pe luni");

        VBox layout = new VBox();
        layout.setSpacing(10);

        try {
            Map<Integer, Integer> lista = service.getTorturiPeLuna();
            for (Map.Entry<Integer, Integer> entry : lista.entrySet()) {
                Label label = new Label("Luna " +entry.getKey() + " Nr: " + entry.getValue());
                layout.getChildren().add(label);
            }
        } catch (Exception ex) {
            Label errorLabel = new Label("Eroare la încărcarea datelor: " + ex.getMessage());
            layout.getChildren().add(errorLabel);
        }

        Scene scene = new Scene(layout, 300, 500);

        nrTortStage.setScene(scene);
        nrTortStage.show();
    }

    private void showCeleMaiVandute() {
        Stage nrTortStage = new Stage();
        nrTortStage.setTitle("Cele mai vandute torturi");

        VBox layout = new VBox();
        layout.setSpacing(10);


        try {
            Map<tort, Integer> lista = service.getTorturiComandate();
            for (Map.Entry<tort, Integer> entry : lista.entrySet()) {
                Label label = new Label("Tort " +entry.getKey() + " Nr: " + entry.getValue());
                layout.getChildren().add(label);
            }
        } catch (Exception ex) {
            Label errorLabel = new Label("Eroare la încărcarea datelor: " + ex.getMessage());
            layout.getChildren().add(errorLabel);
        }

        Scene scene = new Scene(layout, 300, 500);

        nrTortStage.setScene(scene);
        nrTortStage.show();
    }




private void showErrorDialog(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR, message);
    alert.showAndWait();
}

public static void main(String[] args) {

        launch(args);

}
}
