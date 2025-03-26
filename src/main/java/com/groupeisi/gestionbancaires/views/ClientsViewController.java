package com.groupeisi.gestionbancaires.views;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.groupeisi.gestionbancaires.entities.Client;
import com.groupeisi.gestionbancaires.services.ClientService;
import java.io.IOException;
import java.util.List;

public class ClientsViewController {
    @FXML private TextField searchField;
    @FXML private TableView<Client> clientsTable;
    @FXML private TableColumn<Client, Long> idColumn;
    @FXML private TableColumn<Client, String> nomColumn;
    @FXML private TableColumn<Client, String> prenomColumn;
    @FXML private TableColumn<Client, String> emailColumn;
    @FXML private TableColumn<Client, String> telephoneColumn;
    @FXML private TableColumn<Client, String> adresseColumn;
    @FXML private TableColumn<Client, String> statutColumn;

    private ClientService clientService;
    private ObservableList<Client> clients;
    private FilteredList<Client> filteredClients;

    @FXML
    public void initialize() {
        clientService = new ClientService();
        
        // Configuration des colonnes
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));

        // Chargement des données
        loadClients();

        // Configuration de la recherche
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredClients.setPredicate(client -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return client.getNom().toLowerCase().contains(lowerCaseFilter) ||
                       client.getPrenom().toLowerCase().contains(lowerCaseFilter) ||
                       client.getEmail().toLowerCase().contains(lowerCaseFilter);
            });
        });
    }

    private void loadClients() {
        List<Client> clientList = clientService.findAll();
        clients = FXCollections.observableArrayList(clientList);
        filteredClients = new FilteredList<>(clients, b -> true);
        clientsTable.setItems(filteredClients);
    }

    @FXML
    private void handleAddClient() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupeisi/gestionbancaires/views/ClientEditorDialog.fxml"));
            Parent root = loader.load();
            ClientEditorDialogController controller = loader.getController();
            
            Stage stage = new Stage();
            stage.setTitle("Nouveau Client");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (controller.getClient() != null) {
                clientService.save(controller.getClient());
                loadClients();
            }
        } catch (IOException e) {
            showError("Erreur", "Impossible d'ouvrir le formulaire d'ajout de client.");
        }
    }

    @FXML
    private void handleEditClient() {
        Client selectedClient = clientsTable.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            showError("Erreur", "Veuillez sélectionner un client à modifier.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupeisi/gestionbancaires/views/ClientEditorDialog.fxml"));
            Parent root = loader.load();
            ClientEditorDialogController controller = loader.getController();
            controller.setClient(selectedClient);
            
            Stage stage = new Stage();
            stage.setTitle("Modifier Client");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (controller.getClient() != null) {
                clientService.update(controller.getClient());
                loadClients();
            }
        } catch (IOException e) {
            showError("Erreur", "Impossible d'ouvrir le formulaire de modification de client.");
        }
    }

    @FXML
    private void handleDeleteClient() {
        Client selectedClient = clientsTable.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            showError("Erreur", "Veuillez sélectionner un client à supprimer.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer le client");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce client ?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            clientService.delete(selectedClient.getId());
            loadClients();
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