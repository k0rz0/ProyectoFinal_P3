package co.edu.uniquindio.proyectofinal.proyectofinal.viewController;

import co.edu.uniquindio.proyectofinal.proyectofinal.controller.UsuarioController;
import co.edu.uniquindio.proyectofinal.proyectofinal.mapping.dto.UsuarioDTO;
import co.edu.uniquindio.proyectofinal.proyectofinal.rabbit.consumer.controller.ModelFactoryController;
import co.edu.uniquindio.proyectofinal.proyectofinal.utils.RabbitMessageListener;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import static co.edu.uniquindio.proyectofinal.proyectofinal.rabbit.producer.util.Constantes.QUEUE_NUEVA_PUBLICACION;

public class PruebaRabbitViewController implements RabbitMessageListener {

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
    void initialize() {
        usuarioController = new UsuarioController();
        initView();
        modelFactoryController.addRabbitMessageListener(this);
        modelFactoryController.iniciarConsumidor();
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
        crearUsuario(null);
    }

    private void crearUsuario(String message) {
        if (validarCampos()) {
            UsuarioDTO usuarioDTO = contruirUsuarioDTO();
            if (usuarioController.crearUsuario(usuarioDTO)) {
                listaUsuarioDTO.add(usuarioDTO);
                tableUsuario.getItems().clear();
                tableUsuario.getItems().addAll(listaUsuarioDTO);
                tableUsuario.getSortOrder().add(colIdUsuario);
                limpiarCampos();
            }
        }else {
            mostrarMensaje("Notificación Usuario", "Error al crear el usuario", "Por favor llene todos los campos", Alert.AlertType.ERROR);
        }
    }

    private void finalizarAccion(String message, String accion) {
        UsuarioDTO usuarioDTORabbit = recibirDataRabbit(message);
        mostrarMensaje("Notificación Usuario", "Usuario creado", "El Usuario se ha creado con exito", Alert.AlertType.INFORMATION);

        if (accion.equals("actualizar")) {
            listaUsuarioDTO.remove(usuarioDTORabbit);
        } else if (accion.equals("eliminar")) {
            listaUsuarioDTO.remove(usuarioDTORabbit);
            return;
        }

        listaUsuarioDTO.add(usuarioDTORabbit);
        tableUsuario.getItems().clear();
        tableUsuario.getItems().addAll(listaUsuarioDTO);
        tableUsuario.getSortOrder().add(colIdUsuario);
        limpiarCampos();

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

    @Override
    public void onMessageReceived(String message) {
        Platform.runLater(() -> {

            if (message.contains("CREAR_USUARIO")) {
                crearUsuario(message);
            } else if (message.contains("ACTUALIZAR_USUARIO")) {
                actualizarUsuario();
            } else if (message.contains("ELIMINAR_USUARIO")) {
                eliminarUsuario();
            }
        });
    }

    private UsuarioDTO recibirDataRabbit(String message) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                message.split(";")[1],    //id
                message.split(";")[2],    //nombre
                message.split(";")[3],    //correo
                message.split(";")[4],    //telefono
                message.split(";")[5],    //direccion
                message.split(";")[6]);    //saldo

        return usuarioDTO;
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
