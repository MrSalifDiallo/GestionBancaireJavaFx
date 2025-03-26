package application.view;

import com.groupeisi.gestionbancaires.entities.Client;
import com.groupeisi.gestionbancaires.views.ClientEditorDialogController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.List;

public class ClientViewController {
    @FXML private TableView<Client> clientTable;
    @FXML private TableColumn<Client, Integer> idColumn;
    @FXML private TableColumn<Client, String> nomColumn;
    @FXML private TableColumn<Client, String> prenomColumn;
    @FXML private TableColumn<Client, String> emailColumn;
    @FXML private TableColumn<Client, String> telephoneColumn;
    @FXML private TableColumn<Client, String> adresseColumn;
    @FXML private TableColumn<Client, LocalDate> dateInscriptionColumn;
    @FXML private TableColumn<Client, String> statutColumn;

    private ObservableList<Client> clients = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        dateInscriptionColumn.setCellValueFactory(new PropertyValueFactory<>("dateInscription"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));

        clientTable.setItems(clients);
    }

    public void setClients(List<Client> clients) {
        this.clients.setAll(clients);
    }

    @FXML
    private void handleAjouterClient() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupeisi/gestionbancaires/views/ClientEditorDialog.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Nouveau Client");
            stage.setScene(scene);

            ClientEditorDialogController controller = loader.getController();
            Client newClient = new Client();
            controller.setClient(newClient);

            stage.showAndWait();
            if (newClient.getId() != null) {
                clients.add(newClient);
            }
        } catch (Exception e) {
            showError("Erreur", "Impossible d'ouvrir la fenêtre d'édition : " + e.getMessage());
        }
    }

    @FXML
    private void handleModifierClient() {
        Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupeisi/gestionbancaires/views/ClientEditorDialog.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();
                stage.setTitle("Modifier Client");
                stage.setScene(scene);

                ClientEditorDialogController controller = loader.getController();
                controller.setClient(selectedClient);

                stage.showAndWait();
                clientTable.refresh();
            } catch (Exception e) {
                showError("Erreur", "Impossible d'ouvrir la fenêtre d'édition : " + e.getMessage());
            }
        } else {
            showError("Attention", "Veuillez sélectionner un client à modifier");
        }
    }

    @FXML
    private void handleSupprimerClient() {
        Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Supprimer le client");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce client ?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                clients.remove(selectedClient);
                // TODO: Supprimer le client de la base de données
            }
        } else {
            showError("Attention", "Veuillez sélectionner un client à supprimer");
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