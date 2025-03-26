package com.groupeisi.gestionbancaires.views;

import com.groupeisi.gestionbancaires.entities.Client;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ClientEditorDialogController {
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private TextField telephoneField;
    @FXML private TextField adresseField;
    @FXML private ComboBox<String> statutComboBox;

    private Client client;

    @FXML
    public void initialize() {
        statutComboBox.getItems().addAll("ACTIF", "INACTIF");
        if (client == null) {
            client = new Client();
            client.setDateCreation(LocalDateTime.now());
            client.setStatut("ACTIF");
        } else {
            populateFields();
        }
    }

    public void setClient(Client client) {
        this.client = client;
        populateFields();
    }

    private void populateFields() {
        nomField.setText(client.getNom());
        prenomField.setText(client.getPrenom());
        emailField.setText(client.getEmail());
        telephoneField.setText(client.getTelephone());
        adresseField.setText(client.getAdresse());
        statutComboBox.setValue(client.getStatut());
    }

    @FXML
    private void handleSave() {
        if (validateFields()) {
            client.setNom(nomField.getText());
            client.setPrenom(prenomField.getText());
            client.setEmail(emailField.getText());
            client.setTelephone(telephoneField.getText());
            client.setAdresse(adresseField.getText());
            client.setStatut(statutComboBox.getValue());

            closeDialog();
        }
    }

    @FXML
    private void handleCancel() {
        client = null;
        closeDialog();
    }

    private boolean validateFields() {
        if (nomField.getText().trim().isEmpty()) {
            showError("Le nom est requis");
            return false;
        }
        if (prenomField.getText().trim().isEmpty()) {
            showError("Le prénom est requis");
            return false;
        }
        if (emailField.getText().trim().isEmpty()) {
            showError("L'email est requis");
            return false;
        }
        if (telephoneField.getText().trim().isEmpty()) {
            showError("Le téléphone est requis");
            return false;
        }
        if (adresseField.getText().trim().isEmpty()) {
            showError("L'adresse est requise");
            return false;
        }
        if (statutComboBox.getValue() == null) {
            showError("Le statut est requis");
            return false;
        }
        return true;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de validation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeDialog() {
        nomField.getScene().getWindow().hide();
    }

    public Client getClient() {
        return client;
    }
} 