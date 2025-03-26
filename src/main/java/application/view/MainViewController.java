package application.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class MainViewController {
    @FXML
    private BorderPane contentPane;
    @FXML
    private Label lblPageTitle;
    @FXML
    private Button btnClients;
    @FXML
    private Button btnCartes;
    @FXML
    private Button btnTransactions;
    @FXML
    private Button btnAbonnements;
    @FXML
    private Button btnLitiges;
    @FXML
    private Button btnFidelite;
    @FXML
    private Button btnAdmin;

    @FXML
    private void initialize() {
        showClientsView(); // Vue par défaut
    }

    @FXML
    private void showClientsView() {
        loadView("/fxml/ClientView.fxml", "Gestion des Clients");
    }

    @FXML
    private void showCartesView() {
        loadView("/fxml/CarteBancaireView.fxml", "Gestion des Cartes");
    }

    @FXML
    private void showTransactionsView() {
        loadView("/fxml/TransactionView.fxml", "Transactions");
    }

    @FXML
    private void showAbonnementsView() {
        loadView("/fxml/AbonnementView.fxml", "Gestion des Abonnements");
    }

    @FXML
    private void showLitigesView() {
        loadView("/fxml/LitigeView.fxml", "Gestion des Litiges");
    }

    @FXML
    private void showFideliteView() {
        loadView("/fxml/FideliteView.fxml", "Programme Fidélité");
    }

    @FXML
    private void showAdminView() {
        loadView("/fxml/AdminView.fxml", "Administration");
    }

    private void loadView(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            BorderPane view = loader.load();
            contentPane.setCenter(view);
            lblPageTitle.setText(title);
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Ajouter une gestion d'erreur appropriée
        }
    }
} 