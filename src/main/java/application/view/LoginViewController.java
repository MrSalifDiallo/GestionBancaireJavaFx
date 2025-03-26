package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class LoginViewController {
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblError;

    // Identifiants par défaut pour la démonstration
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "admin123";

    @FXML
    private void handleLogin() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (isValidLogin(username, password)) {
            try {
                // Charger la vue principale
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/MainView.fxml"));
                BorderPane mainView = loader.load();

                // Créer une nouvelle scène
                Scene scene = new Scene(mainView);
                scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

                // Obtenir la fenêtre actuelle et afficher la nouvelle scène
                Stage stage = (Stage) txtUsername.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Gestion Bancaire - Administration");
                stage.setMaximized(true);

            } catch (IOException e) {
                e.printStackTrace();
                lblError.setText("Erreur lors du chargement de l'application");
            }
        } else {
            lblError.setText("Identifiants incorrects");
            lblError.setVisible(true);
        }
    }

    private boolean isValidLogin(String username, String password) {
        // TODO: Implémenter la vérification avec la base de données
        return username.equals(DEFAULT_USERNAME) && password.equals(DEFAULT_PASSWORD);
    }
} 