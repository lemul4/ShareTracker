module com.example.sharetracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sdk.core;
    requires java.sdk.grpc.contract;
    requires java.logging;
    requires org.slf4j;


    opens com.example.sharetracker to javafx.fxml;
    exports com.example.sharetracker;
}