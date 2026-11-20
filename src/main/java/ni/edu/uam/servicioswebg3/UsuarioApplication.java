package ni.edu.uam.servicioswebg3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UsuarioApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Main-view.fxml contiene las pestañas "Usuarios" y "Clientes"
        FXMLLoader fxmlLoader = new FXMLLoader(UsuarioApplication.class.getResource("Main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Servicios Web G3");
        stage.setScene(scene);
        stage.show();
    }
}
