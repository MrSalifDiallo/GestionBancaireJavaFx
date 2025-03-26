package com.groupeisi.gestionbancaires;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Optional;

public class MainViewController {
    @FXML
    private VBox contentArea;

    @FXML
    private void handleClients() {
        loadView("/com/groupeisi/gestionbancaires/views/ClientView.fxml");
    }

    @FXML
    private void handleCartesBancaires() {
        loadView("/com/groupeisi/gestionbancaires/views/CarteBancaireView.fxml");
    }

    @FXML
    private void handleTransactions() {
        loadView("/com/groupeisi/gestionbancaires/views/TransactionView.fxml");
    }

    @FXML
    private void handleAbonnements() {
        loadView("/com/groupeisi/gestionbancaires/views/AbonnementView.fxml");
    }

    @FXML
    private void handleLitiges() {
        loadView("/com/groupeisi/gestionbancaires/views/LitigeView.fxml");
    }

    @FXML
    private void handleFidelite() {
        loadView("/com/groupeisi/gestionbancaires/views/FideliteView.fxml");
    }

    @FXML
    private void handleDeconnexion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText("Confirmer la déconnexion");
        alert.setContentText("Êtes-vous sûr de vouloir vous déconnecter ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Fermer la fenêtre principale
                contentArea.getScene().getWindow().hide();

                // Charger la vue de connexion
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/groupeisi/gestionbancaires/views/LoginView.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

                // Créer une nouvelle fenêtre
                javafx.stage.Stage stage = new javafx.stage.Stage();
                stage.setTitle("Connexion");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                showError("Erreur lors de la déconnexion", e.getMessage());
            }
        }
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            VBox view = fxmlLoader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            showError("Erreur de chargement", "Impossible de charger la vue : " + e.getMessage());
        }
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 