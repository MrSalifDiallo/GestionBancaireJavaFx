package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    @FXML
    private BorderPane mainContainer;
    @FXML
    private Label lblUserName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialisation du contrôleur
        setupUI();
    }

    private void setupUI() {
        // Configuration initiale de l'interface utilisateur
        lblUserName.setText("Admin"); // À remplacer par le nom de l'utilisateur connecté
    }

    @FXML
    private void handleClients() {
        // Charger la vue des clients
        loadView("/fxml/ClientView.fxml");
    }

    @FXML
    private void handleCartes() {
        // Charger la vue des cartes
        loadView("/fxml/CarteView.fxml");
    }

    @FXML
    private void handleTransactions() {
        // Charger la vue des transactions
        loadView("/fxml/TransactionView.fxml");
    }

    @FXML
    private void handleLitiges() {
        // Charger la vue des litiges
        loadView("/fxml/LitigeView.fxml");
    }

    @FXML
    private void handleFidelite() {
        // Charger la vue du programme de fidélité
        loadView("/fxml/FideliteView.fxml");
    }

    @FXML
    private void handleAbonnements() {
        // Charger la vue des abonnements
        loadView("/fxml/AbonnementView.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            // Charger la vue dans le conteneur principal
            mainContainer.setCenter(null); // Nettoyer la vue actuelle
            mainContainer.setCenter(new javafx.fxml.FXMLLoader(
                getClass().getResource(fxmlPath)).load());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 