package ni.edu.uam.servicioswebg3.Controller;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ni.edu.uam.servicioswebg3.model.Cliente;
import ni.edu.uam.servicioswebg3.service.ClienteService;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

public class ClienteController {

    @FXML
    private TableView<Cliente> tablaClientes;
    @FXML
    private TableColumn<Cliente, Integer> colId;
    @FXML
    private TableColumn<Cliente, String> colNombre;
    @FXML
    private TableColumn<Cliente, String> colEmail;
    @FXML
    private TableColumn<Cliente, String> colTelefono;
    @FXML
    private TableColumn<Cliente, LocalDate> colFechaNacimiento;
    @FXML
    private TableColumn<Cliente, String> colPais;
    @FXML
    private TableColumn<Cliente, String> colCiudad;
    @FXML
    private TableColumn<Cliente, String> colDireccion;
    @FXML
    private TableColumn<Cliente, Double> colIngreso;
    @FXML
    private TableColumn<Cliente, Integer> colPuntaje;
    @FXML
    private TableColumn<Cliente, Boolean> colTieneDeuda;
    @FXML
    private TableColumn<Cliente, Double> colMontoDeuda;
    @FXML
    private TableColumn<Cliente, String> colEstado;
    @FXML
    private Button btnConsultar;
    @FXML
    private Label lblEstado;

    private final ClienteService service = new ClienteService();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colIngreso.setCellValueFactory(new PropertyValueFactory<>("ingresoAnual"));
        colPuntaje.setCellValueFactory(new PropertyValueFactory<>("puntajeCredito"));
        colTieneDeuda.setCellValueFactory(new PropertyValueFactory<>("tieneDeuda"));
        colMontoDeuda.setCellValueFactory(new PropertyValueFactory<>("montoDeuda"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Formato de visualización
        formatear(colIngreso, valor -> String.format("%,.2f", valor));
        formatear(colMontoDeuda, valor -> String.format("%,.2f", valor));
        formatear(colTieneDeuda, valor -> valor ? "Sí" : "No");
    }

    @FXML
    private void consultarClientes() {
        Task<List<Cliente>> task = new Task<>() {
            @Override
            protected List<Cliente> call() throws Exception {
                return service.obtenerClientes();
            }
        };

        btnConsultar.disableProperty().bind(task.runningProperty());

        task.setOnSucceeded(e -> {
            List<Cliente> clientes = task.getValue();
            tablaClientes.setItems(FXCollections.observableArrayList(clientes));
            lblEstado.setText("Clientes cargados: " + clientes.size());
        });

        task.setOnFailed(e -> {
            Throwable error = task.getException();
            lblEstado.setText("Error: " + error.getMessage());
        });

        Thread hilo = new Thread(task);
        hilo.setDaemon(true);
        hilo.start();
    }

    // Muestra el valor de la celda con el formato indicado
    private <T> void formatear(TableColumn<Cliente, T> columna, Function<T, String> formato) {
        columna.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : formato.apply(item));
            }
        });
    }
}
