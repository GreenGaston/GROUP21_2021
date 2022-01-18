module com.example.knapsack {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.Application to javafx.fxml;
    exports com.example.Application;
}