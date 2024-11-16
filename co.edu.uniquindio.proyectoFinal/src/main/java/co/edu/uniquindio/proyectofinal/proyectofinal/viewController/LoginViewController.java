package co.edu.uniquindio.proyectofinal.proyectofinal.viewController;

import co.edu.uniquindio.proyectofinal.proyectofinal.BilleteraVirtual;
import co.edu.uniquindio.proyectofinal.proyectofinal.controller.LoginController;
import co.edu.uniquindio.proyectofinal.proyectofinal.controller.UsuarioController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginViewController {
    LoginController loginController;
    String adminPath = "crud-usuario-view.fxml";
    String userPath = "crud-usuario-view.fxml";
    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUser;
    @FXML
    void initialize() {
        loginController = new LoginController();
    }
    @FXML
    void onSigIn(ActionEvent event) {
        crearVentana(event,"register-view.fxml");
    }
    @FXML
    void onEnter(ActionEvent event) {
        if (!txtPassword.getText().isEmpty() && !txtUser.getText().isEmpty()) { // Ambos campos deben estar llenos
            int numValidation = loginController.validation(txtUser.getText(), txtPassword.getText());

            if (numValidation != 0) { // Validación exitosa
                String fxmlPath = numValidation == 1 ? adminPath : userPath;
                crearVentana(event,fxmlPath);


            }
        } else {
            txtPassword.requestFocus(); // Pedir enfoque si los campos están vacíos
        }
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


}
