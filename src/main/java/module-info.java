module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires stanford.corenlp;
    requires GoogleTranslatorTTS;
    requires javafx.media;


    opens com.example.demo to javafx.fxml;
    opens com.example.demo.guii to javafx.fxml;

    exports com.example.demo;
    exports com.example.demo.guii to javafx.fxml;

}