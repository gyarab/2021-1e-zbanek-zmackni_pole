module com.example.rp2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.rp2 to javafx.fxml;
    exports com.example.rp2;
}