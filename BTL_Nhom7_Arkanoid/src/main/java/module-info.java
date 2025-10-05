module org.example.btl_nhom7_arkanoid {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.btl_nhom7_arkanoid to javafx.fxml;
    exports org.example.btl_nhom7_arkanoid;
}