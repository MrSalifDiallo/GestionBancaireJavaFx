package application.view;

import com.groupeisi.gestionbancaires.entities.Client;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClientEditorPaneViewController {
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private TextField telephoneField;
    @FXML private TextField adresseField;
    @FXML private CheckBox actifCheckBox;
    @FXML private Label errorLabel;

    private Client client;

    @FXML
    public void initialize() {
        if (client != null) {
            nomField.setText(client.getNom());
            prenomField.setText(client.getPrenom());
            emailField.setText(client.getEmail());
            telephoneField.setText(client.getTelephone());
            adresseField.setText(client.getAdresse());
            actifCheckBox.setSelected("ACTIF".equals(client.getStatut()));
        }
    }

    public void setClient(Client client) {
        this.client = client;
        initialize();
    }

    @FXML
    private void handleOk() {
        if (validateInput()) {
            client.setNom(nomField.getText());
            client.setPrenom(prenomField.getText());
            client.setEmail(emailField.getText());
            client.setTelephone(telephoneField.getText());
            client.setAdresse(adresseField.getText());
            client.setStatut(actifCheckBox.isSelected() ? "ACTIF" : "INACTIF");
            if (client.getId() == null) {
                client.setDateCreation(LocalDateTime.now());
                client.setStatut("ACTIF");
            }

            Stage stage = (Stage) nomField.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) nomField.getScene().getWindow();
        stage.close();
    }

    private boolean validateInput() {
        if (nomField.getText().trim().isEmpty()) {
            errorLabel.setText("Le nom est requis");
            return false;
        }
        if (prenomField.getText().trim().isEmpty()) {
            errorLabel.setText("Le prénom est requis");
            return false;
        }
        if (emailField.getText().trim().isEmpty()) {
            errorLabel.setText("L'email est requis");
            return false;
        }
        if (telephoneField.getText().trim().isEmpty()) {
            errorLabel.setText("Le téléphone est requis");
            return false;
        }
        if (adresseField.getText().trim().isEmpty()) {
            errorLabel.setText("L'adresse est requise");
            return false;
        }
        return true;
    }
} 