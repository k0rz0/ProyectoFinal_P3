package co.edu.uniquindio.proyectofinal.proyectofinal.viewController;

import co.edu.uniquindio.proyectofinal.proyectofinal.controller.LoginController;
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
    LoginController loginController = new LoginController();
    String adminPath = "/co/edu/uniquindio/proyectofinal/proyectofinal/crud-usuario-view.fxml";
    String userPath = "/co/edu/uniquindio/proyectofinal/proyectofinal/crud-usuario-view.fxml";
    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUser;

    @FXML
    void onEnter(ActionEvent event) {
        if (!txtPassword.getText().isEmpty() && !txtUser.getText().isEmpty()) { // Ambos campos deben estar llenos
            int numValidation = loginController.validation(txtUser.getText(), txtPassword.getText());

            if (numValidation != 0) { // Validación exitosa
                try {
                    String fxmlPath = numValidation == 1 ? adminPath : userPath;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                    Parent root = loader.load();
                    Scene newScene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(newScene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            txtPassword.requestFocus(); // Pedir enfoque si los campos están vacíos
        }
    }

}
