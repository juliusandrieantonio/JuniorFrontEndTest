module org.example.frondendtest {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires com.fasterxml.jackson.dataformat.csv;


    exports org.example.frondendtest.utils.models;
    exports org.example.frondendtest.utils.constants;

    exports org.example.frondendtest.ui.Main;
    opens org.example.frondendtest.ui.Main to javafx.fxml;
    exports org.example.frondendtest.ui.card;
    opens org.example.frondendtest.ui.card to javafx.fxml;
}