package com.groupeisi.gestionbancaires.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginViewController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // TODO: Implémenter la vérification avec la base de données
        if ("admin".equals(username) && "admin".equals(password)) {
            try {
                // Charger la vue principale
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupeisi/gestionbancaires/views/MainView.fxml"));
                Scene scene = new Scene(loader.load());
                
                // Ajouter les styles CSS
                var cssUrl = getClass().getResource("/com/groupeisi/gestionbancaires/styles/styles.css");
                if (cssUrl != null) {
                    scene.getStylesheets().add(cssUrl.toExternalForm());
                }
                
                // Obtenir la fenêtre actuelle et changer sa scène
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Gestion Bancaire - Menu Principal");
                stage.setMaximized(true);
                
            } catch (Exception e) {
                errorLabel.setText("Erreur lors du chargement de la vue principale");
                errorLabel.setVisible(true);
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Nom d'utilisateur ou mot de passe incorrect");
            errorLabel.setVisible(true);
        }
    }
} 