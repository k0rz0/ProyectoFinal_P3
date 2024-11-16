package co.edu.uniquindio.proyectofinal.proyectofinal.viewController;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinal.proyectofinal.rabbit.producer.controller.ModelFactoryController;
import co.edu.uniquindio.proyectofinal.proyectofinal.controller.UsuarioController;
import co.edu.uniquindio.proyectofinal.proyectofinal.mapping.dto.UsuarioDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import static co.edu.uniquindio.proyectofinal.proyectofinal.rabbit.producer.util.Constantes.QUEUE_NUEVA_PUBLICACION;

public class UsuarioViewController {
    UsuarioController usuarioController;
    ObservableList<UsuarioDTO> listaUsuarioDTO = FXCollections.observableArrayList();
    UsuarioDTO usuarioSeleccionado;
    ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView<UsuarioDTO> tableUsuario;

    @FXML
    private TableColumn<UsuarioDTO, String> colIdUsuario;

    @FXML
    private TableColumn<UsuarioDTO, String> colNombre;

    @FXML
    private TableColumn<UsuarioDTO, String> colCorreo;

    @FXML
    private TableColumn<UsuarioDTO, String> colTelefono;

    @FXML
    private TableColumn<UsuarioDTO, String> colDireccion;

    @FXML
    private TableColumn<UsuarioDTO, String> colSaldo;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtIdUsuario;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtSaldo;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtBuscarUsuario;

    @FXML
    void onCleanSearch(ActionEvent event) {
        txtBuscarUsuario.setText("");
    }
    @FXML
    void initialize() {
        usuarioController = new UsuarioController();
        initView();
        initSearch();
    }
    private void initSearch(){

        FilteredList<UsuarioDTO> filteredData = new FilteredList<>(listaUsuarioDTO, b->true);
        txtBuscarUsuario.textProperty().addListener((ObservableList,oldValue,newValue)->{
            filteredData.setPredicate(usuarioSeleccionado ->{
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String loweCaseFilter = newValue.toLowerCase();
                if (usuarioSeleccionado.nombre().toLowerCase().contains(loweCaseFilter)){
                    return true;
                }
                return usuarioSeleccionado.idUsuario().toLowerCase().contains(loweCaseFilter);
            });
        });
        SortedList<UsuarioDTO> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableUsuario.comparatorProperty());
        tableUsuario.setItems(sortedData);
    }

    private void initView() {
        initDataBinding();
        obtenerUsuarios();
        tableUsuario.getItems().clear();
        tableUsuario.getItems().addAll(listaUsuarioDTO);
        listenerSelection();
    }

    private void listenerSelection() {
        tableUsuario.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            usuarioSeleccionado = newValue;
            mostrarInformacionUsuario(usuarioSeleccionado);
        });
    }

    private void mostrarInformacionUsuario(UsuarioDTO usuarioSeleccionado) {

        if (usuarioSeleccionado != null) {
            txtIdUsuario.setText(String.valueOf(usuarioSeleccionado.idUsuario()));
            txtNombre.setText(String.valueOf(usuarioSeleccionado.nombre()));
            txtCorreo.setText(String.valueOf(usuarioSeleccionado.correo()));
            txtTelefono.setText(String.valueOf(usuarioSeleccionado.telefono()));
            txtDireccion.setText(String.valueOf(usuarioSeleccionado.direccion()));
            txtSaldo.setText(String.valueOf(usuarioSeleccionado.saldo()));
        }
    }

    private void obtenerUsuarios() {
        listaUsuarioDTO.addAll(usuarioController.obtenerUsuarios());
    }

    private void initDataBinding() {
        colIdUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().idUsuario()));
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        colCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().correo()));
        colTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().telefono()));
        colDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().direccion()));
        colSaldo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().saldo()));

    }

    @FXML
    void onCrearUsuario(ActionEvent event) {
        crearUsuario();
    }

    private void crearUsuario() {
        if (validarCampos()) {
            UsuarioDTO usuarioDTO = contruirUsuarioDTO();
            if (usuarioController.crearUsuario(usuarioDTO)) {
                mostrarMensaje("Notificación Usuario", "Usuario creado", "El Usuario se ha creado con exito", Alert.AlertType.INFORMATION);

                listaUsuarioDTO.add(usuarioDTO);
                tableUsuario.getItems().clear();
                tableUsuario.getItems().addAll(listaUsuarioDTO);
                tableUsuario.getSortOrder().add(colIdUsuario);
                limpiarCampos();

                enviarDataRabbit(usuarioDTO);
            }
        }else {
            mostrarMensaje("Notificación Usuario", "Error al crear el usuario", "Por favor llene todos los campos", Alert.AlertType.ERROR);
        }
    }

    private void enviarDataRabbit(UsuarioDTO usuarioDTO) {

        String mensaje = "";
        mensaje += "CREAR_USUARIO;";
        mensaje += usuarioDTO.idUsuario() + ";";
        mensaje += usuarioDTO.nombre() + ";";
        mensaje += usuarioDTO.correo() + ";";
        mensaje += usuarioDTO.telefono() + ";";
        mensaje += usuarioDTO.direccion() + ";";
        mensaje += usuarioDTO.saldo() + ";";

        modelFactoryController.producirMensaje(QUEUE_NUEVA_PUBLICACION, mensaje);
    }

    private void limpiarCampos() {
        txtIdUsuario.setText("");
        txtNombre.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtSaldo.setText("");
        txtDireccion.setText("");
    }

    private UsuarioDTO contruirUsuarioDTO() {
        return new UsuarioDTO(txtIdUsuario.getText(), txtNombre.getText(), txtCorreo.getText(), txtTelefono.getText(), txtDireccion.getText(), txtSaldo.getText());
    }

    @FXML
    void onActualizarUsuario(ActionEvent event) {
        actualizarUsuario();
    }

    private void actualizarUsuario() {
        if (validarCampos()) {
            UsuarioDTO usuarioDTO = contruirUsuarioDTO();
            if (usuarioController.actualizarUsuario(usuarioDTO)) {
                mostrarMensaje("Notificación Usuario", "Usuario actualizado", "El Usuario se ha actualizado con exito", Alert.AlertType.INFORMATION);

                listaUsuarioDTO.remove(usuarioSeleccionado);
                listaUsuarioDTO.add(usuarioDTO);
                tableUsuario.getItems().clear();
                tableUsuario.getItems().addAll(listaUsuarioDTO);
                tableUsuario.getSortOrder().add(colIdUsuario);
                limpiarCampos();

            }
        } else {
            mostrarMensaje("Notificación Usuario", "Error al actualizar el usuario", "Por favor llene todos los campos", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onEliminarUsuario(ActionEvent event) {
        eliminarUsuario();
    }

    private void eliminarUsuario() {
        if (validarCampos()) {
            UsuarioDTO usuarioDTO = contruirUsuarioDTO();
            if (usuarioController.eliminarUsuario(usuarioDTO)) {
                mostrarMensaje("Notificación Usuario", "Usuario eliminado", "El Usuario se ha eliminado con exito", Alert.AlertType.INFORMATION);
                listaUsuarioDTO.remove(usuarioSeleccionado);
                tableUsuario.getItems().clear();
                tableUsuario.getItems().addAll(listaUsuarioDTO);
                tableUsuario.getSortOrder().add(colIdUsuario);
                limpiarCampos();
            }
        }
    }

    private boolean validarCampos() {
        if (txtNombre.getText().isEmpty() || txtCorreo.getText().isEmpty() ||
                txtSaldo.getText().isEmpty() || txtTelefono.getText().isEmpty() ||
                txtIdUsuario.getText().isEmpty() || txtDireccion.getText().isEmpty()) {
            return false;
        } else{
            return true;
        }
    }
    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

}
