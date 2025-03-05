module org.main {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;

    opens a4 to javafx.fxml;
    exports a4;

    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires javafaker;

}
