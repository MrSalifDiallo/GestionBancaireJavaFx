package com.groupeisi.gestionbancaires.views;

import com.groupeisi.gestionbancaires.entities.Client;
import com.groupeisi.gestionbancaires.JpaUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.io.IOException;
import java.util.List;

public class ClientViewController {
    @FXML
    private TableView<Client> clientTable;

    @FXML
    private TableColumn<Client, Integer> colId;
    @FXML
    private TableColumn<Client, String> colNom;
    @FXML
    private TableColumn<Client, String> colPrenom;
    @FXML
    private TableColumn<Client, String> colEmail;
    @FXML
    private TableColumn<Client, String> colTelephone;
    @FXML
    private TableColumn<Client, String> colAdresse;
    @FXML
    private TableColumn<Client, String> colDateInscription;
    @FXML
    private TableColumn<Client, String> colStatut;

    private ObservableList<Client> clients = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Configurer les colonnes
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        colDateInscription.setCellValueFactory(new PropertyValueFactory<>("dateInscription"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        // Charger les clients
        loadClients();
    }

    private void loadClients() {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c", Client.class);
            List<Client> clientList = query.getResultList();
            clients.setAll(clientList);
            clientTable.setItems(clients);
        } catch (Exception e) {
            showError("Erreur", "Impossible de charger les clients : " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @FXML
    private void handleNouveauClient() {
        showClientEditorDialog(null);
    }

    @FXML
    private void handleModifierClient() {
        Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            showClientEditorDialog(selectedClient);
        } else {
            showError("Erreur", "Veuillez sélectionner un client à modifier");
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
                EntityManager em = JpaUtils.getEntityManager();
                try {
                    em.getTransaction().begin();
                    em.remove(em.merge(selectedClient));
                    em.getTransaction().commit();
                    loadClients();
                } catch (Exception e) {
                    em.getTransaction().rollback();
                    showError("Erreur", "Impossible de supprimer le client : " + e.getMessage());
                } finally {
                    em.close();
                }
            }
        } else {
            showError("Erreur", "Veuillez sélectionner un client à supprimer");
        }
    }

    private void showClientEditorDialog(Client client) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientEditorDialog.fxml"));
            DialogPane dialogPane = loader.load();
            ClientEditorDialogController controller = loader.getController();
            controller.setClient(client);

            Dialog<Client> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setResultConverter(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    return controller.getClient();
                }
                return null;
            });

            dialog.showAndWait().ifPresent(updatedClient -> {
                EntityManager em = JpaUtils.getEntityManager();
                try {
                    em.getTransaction().begin();
                    if (client == null) {
                        em.persist(updatedClient);
                    } else {
                        em.merge(updatedClient);
                    }
                    em.getTransaction().commit();
                    loadClients();
                } catch (Exception e) {
                    em.getTransaction().rollback();
                    showError("Erreur", "Impossible de sauvegarder le client : " + e.getMessage());
                } finally {
                    em.close();
                }
            });
        } catch (IOException e) {
            showError("Erreur", "Impossible de charger le dialogue : " + e.getMessage());
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