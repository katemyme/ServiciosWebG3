module ni.edu.uam.servicioswebg3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;


    opens ni.edu.uam.servicioswebg3 to javafx.fxml;
    exports ni.edu.uam.servicioswebg3;
    exports ni.edu.uam.servicioswebg3.Controller;
    opens ni.edu.uam.servicioswebg3.Controller to javafx.fxml;
    opens ni.edu.uam.servicioswebg3.model to javafx.base;
}