package co.edu.uniquindio.proyectofinal.proyectofinal.viewController;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinal.proyectofinal.controller.CuentaController;
import co.edu.uniquindio.proyectofinal.proyectofinal.mapping.dto.CuentaDTO;
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
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import static co.edu.uniquindio.proyectofinal.proyectofinal.rabbit.producer.util.Constantes.QUEUE_NUEVA_PUBLICACION;

public class CuentaViewController{
    CuentaController cuentaController;
    ObservableList<CuentaDTO> listaCuentaDTO = FXCollections.observableArrayList();
    CuentaDTO cuentaDTOSeleccionada;
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
    private TableColumn<CuentaDTO, String> colIdCuenta;

    @FXML
    private TableColumn<CuentaDTO, String> colNombreBanco;

    @FXML
    private TableColumn<CuentaDTO, String> colNumeroCuenta;

    @FXML
    private TableColumn<CuentaDTO, String> colTipoCuenta;

    @FXML
    private TableView<CuentaDTO> tableCuenta;

    @FXML
    private TextField txtIdCuenta;

    @FXML
    private TextField txtNombreBanco;

    @FXML
    private TextField txtNumeroCuenta;

    @FXML
    private TextField txtTipoCuenta;

    @FXML
    private TextField txtBuscarCuenta;

    @FXML
    void onCleanSearch(ActionEvent event) {
        txtBuscarCuenta.setText("");
    }
    @FXML
    void initialize() {
        cuentaController = new CuentaController();
        initView();
        initSearch();
    }
    private void initSearch(){

        FilteredList<CuentaDTO> filteredData = new FilteredList<>(listaCuentaDTO, b->true);
        txtBuscarCuenta.textProperty().addListener((ObservableList,oldValue,newValue)->{
            filteredData.setPredicate(cuentaDTOSeleccionada ->{
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String loweCaseFilter = newValue.toLowerCase();
                if (cuentaDTOSeleccionada.numeroCuenta().toLowerCase().contains(loweCaseFilter)){
                    return true;
                }
                return String.valueOf(cuentaDTOSeleccionada.idCuenta()).toLowerCase().contains(loweCaseFilter) ;
            });
        });
        SortedList<CuentaDTO> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableCuenta.comparatorProperty());
        tableCuenta.setItems(sortedData);
    }

    private void initView() {
        initDataBinding();
        obtenerCuentas();
        tableCuenta.getItems().clear();
        tableCuenta.getItems().addAll(listaCuentaDTO);
        listenerSelection();
    }

    private void listenerSelection() {
        tableCuenta.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cuentaDTOSeleccionada = newValue;
            mostrarInformacionCuenta(construirCuentaDTO());
        });
    }

    private void mostrarInformacionCuenta(CuentaDTO cuentaSeleccionada) {

        if (cuentaSeleccionada != null) {
            txtIdCuenta.setText(String.valueOf(cuentaSeleccionada.idCuenta()));
            txtNombreBanco.setText(String.valueOf(cuentaSeleccionada.nombreBanco()));
            txtNumeroCuenta.setText(String.valueOf(cuentaSeleccionada.numeroCuenta()));
            txtTipoCuenta.setText(String.valueOf(cuentaSeleccionada.tipoCuenta()));
        }
    }

    private void obtenerCuentas() {
        listaCuentaDTO.addAll(cuentaController.obtenerCuentas());
    }

    private void initDataBinding() {
        colIdCuenta.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().idCuenta()));
        colNombreBanco.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombreBanco()));
        colNumeroCuenta.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().numeroCuenta()));
        colTipoCuenta.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().tipoCuenta()));
    }

    @FXML
    void onCrearCuenta(ActionEvent event) {
        crearCuenta();
    }

    private void crearCuenta() {
        if (validarCampos()) {
            CuentaDTO cuentaDTO = construirCuentaDTO();
            if (cuentaController.crearCuenta(cuentaDTO)) {
                mostrarMensaje("Notificación Cuenta", "Cuenta creada", "La cuenta se ha creado con exito", Alert.AlertType.INFORMATION);

                listaCuentaDTO.add(cuentaDTO);
                tableCuenta.getItems().clear();
                tableCuenta.getItems().addAll(listaCuentaDTO);
                tableCuenta.getSortOrder().add(colIdCuenta);
                limpiarCampos();
            }
        }else {
            mostrarMensaje("Notificación Cuenta", "Error al crear el cuenta", "Por favor llene todos los campos", Alert.AlertType.ERROR);
        }
    }

    private void limpiarCampos() {
        txtIdCuenta.setText("");
        txtNombreBanco.setText("");
        txtNumeroCuenta.setText("");
        txtTipoCuenta.setText("");
    }

    private CuentaDTO construirCuentaDTO() {
        return new CuentaDTO(txtIdCuenta.getText(), txtNombreBanco.getText(), txtNumeroCuenta.getText(), txtTipoCuenta.getText());
    }

    @FXML
    void onActualizarCuenta(ActionEvent event) {
        actualizarCuenta();
    }

    private void actualizarCuenta() {
        if (validarCampos()) {
            CuentaDTO cuentaDTO = construirCuentaDTO();
            if (cuentaController.actualizarCuenta(cuentaDTO)) {
                mostrarMensaje("Notificación Cuenta", "Cuenta actualizada", "La cuenta se ha actualizado con exito", Alert.AlertType.INFORMATION);

                listaCuentaDTO.remove(cuentaDTO);
                listaCuentaDTO.add(cuentaDTO);
                tableCuenta.getItems().clear();
                tableCuenta.getItems().addAll(listaCuentaDTO);
                tableCuenta.getSortOrder().add(colIdCuenta);
                limpiarCampos();

            }
        } else {
            mostrarMensaje("Notificación Cuenta", "Error al actualizar el cuenta", "Por favor llene todos los campos", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onEliminarCuenta(ActionEvent event) {
        eliminarUsuario();
    }

    private void eliminarUsuario() {
        if (validarCampos()) {
            CuentaDTO cuentaDTO = construirCuentaDTO();
            if (cuentaController.eliminarCuenta(cuentaDTO)) {
                mostrarMensaje("Notificación Cuenta", "Cuenta eliminado", "La Cuenta se ha eliminado con exito", Alert.AlertType.INFORMATION);
                listaCuentaDTO.remove(cuentaDTO);
                tableCuenta.getItems().clear();
                tableCuenta.getItems().addAll(listaCuentaDTO);
                tableCuenta.getSortOrder().add(colIdCuenta);
                limpiarCampos();
            }
        }
    }

    private boolean validarCampos() {
        if (txtIdCuenta.getText().isEmpty() || txtNombreBanco.getText().isEmpty() ||
                txtNumeroCuenta.getText().isEmpty() || txtTipoCuenta.getText().isEmpty()) {
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
