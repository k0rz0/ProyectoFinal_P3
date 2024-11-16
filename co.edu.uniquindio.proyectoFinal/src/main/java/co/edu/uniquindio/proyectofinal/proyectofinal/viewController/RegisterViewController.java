package co.edu.uniquindio.proyectofinal.proyectofinal.viewController;

import co.edu.uniquindio.proyectofinal.proyectofinal.BilleteraVirtual;
import co.edu.uniquindio.proyectofinal.proyectofinal.controller.LoginController;
import co.edu.uniquindio.proyectofinal.proyectofinal.controller.RegistrarController;
import co.edu.uniquindio.proyectofinal.proyectofinal.mapping.dto.UsuarioDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterViewController {
    RegistrarController registrarController;
    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtCedula;

    @FXML
    void onSignIn(ActionEvent event) {
        if (!validacionCampos()){
            UsuarioDTO usuario = construirUsuario();
            crearVentana(event,"crud-usuario-view.fxml");
//            if (registrarController.registrarUsuario(usuario)){
//                System.out.println("hola");
//            }
        }
    }

    @FXML
    void onSignUp(ActionEvent event) {
        crearVentana(event,"login-view.fxml");
    }
    private void crearVentana(ActionEvent event,String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(BilleteraVirtual.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void initialize() {
        registrarController  = new RegistrarController();
    }

    private UsuarioDTO construirUsuario() {
        return new UsuarioDTO(txtCedula.getText(), txtName.getText(), txtEmail.getText(), txtLastName.getText(), txtAddress.getText(), "0");
    }

    private boolean validacionCampos() {
        return txtAddress.getText().isEmpty() || txtEmail.getText().isEmpty() || txtLastName.getText().isEmpty() || txtName.getText().isEmpty() || txtCedula.getText().isEmpty();
    }


}
