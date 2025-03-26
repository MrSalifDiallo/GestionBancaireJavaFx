package com.groupeisi.gestionbancaires.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainViewController {
    @FXML private VBox contentArea;
    @FXML private Label dashboardNav;
    @FXML private Label clientsNav;
    @FXML private Label cartesNav;
    @FXML private Label transactionsNav;
    @FXML private Label authentificationNav;
    @FXML private Label abonnementsNav;
    @FXML private Label litigesNav;
    @FXML private Label fideliteNav;

    @FXML
    public void initialize() {
        // Activer le dashboard par défaut
        handleDashboard();
    }

    private void setActiveNav(Label activeNav) {
        // Réinitialiser tous les éléments de navigation
        dashboardNav.getStyleClass().remove("active");
        clientsNav.getStyleClass().remove("active");
        cartesNav.getStyleClass().remove("active");
        transactionsNav.getStyleClass().remove("active");
        authentificationNav.getStyleClass().remove("active");
        abonnementsNav.getStyleClass().remove("active");
        litigesNav.getStyleClass().remove("active");
        fideliteNav.getStyleClass().remove("active");

        // Activer l'élément sélectionné
        activeNav.getStyleClass().add("active");
    }

    @FXML
    private void handleDashboard() {
        setActiveNav(dashboardNav);
        loadView("/com/groupeisi/gestionbancaires/views/DashboardView.fxml");
    }

    @FXML
    private void handleClients() {
        setActiveNav(clientsNav);
        loadView("/com/groupeisi/gestionbancaires/views/ClientView.fxml");
    }

    @FXML
    private void handleCartesBancaires() {
        setActiveNav(cartesNav);
        loadView("/com/groupeisi/gestionbancaires/views/CarteBancaireView.fxml");
    }

    @FXML
    private void handleTransactions() {
        setActiveNav(transactionsNav);
        loadView("/com/groupeisi/gestionbancaires/views/TransactionView.fxml");
    }

    @FXML
    private void handleAuthentification() {
        setActiveNav(authentificationNav);
        loadView("/com/groupeisi/gestionbancaires/views/AuthentificationView.fxml");
    }

    @FXML
    private void handleAbonnements() {
        setActiveNav(abonnementsNav);
        loadView("/com/groupeisi/gestionbancaires/views/AbonnementView.fxml");
    }

    @FXML
    private void handleLitiges() {
        setActiveNav(litigesNav);
        loadView("/com/groupeisi/gestionbancaires/views/LitigeView.fxml");
    }

    @FXML
    private void handleFidelite() {
        setActiveNav(fideliteNav);
        loadView("/com/groupeisi/gestionbancaires/views/FideliteView.fxml");
    }

    @FXML
    private void handleDeconnexion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupeisi/gestionbancaires/views/LoginView.fxml"));
            Scene scene = new Scene(loader.load());
            
            // Ajouter les styles CSS
            var cssUrl = getClass().getResource("/com/groupeisi/gestionbancaires/styles/styles.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }
            
            Stage stage = (Stage) contentArea.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Gestion Bancaire - Connexion");
            stage.setMaximized(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            VBox view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 