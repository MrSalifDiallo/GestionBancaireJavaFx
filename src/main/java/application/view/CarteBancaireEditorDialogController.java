package application.view;

import com.groupeisi.gestionbancaires.entities.CarteBancaire;
import com.groupeisi.gestionbancaires.entities.Client;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.List;

public class CarteBancaireEditorDialogController {
    @FXML private TextField numeroField;
    @FXML private DatePicker dateExpirationPicker;
    @FXML private TextField cvvField;
    @FXML private TextField soldeField;
    @FXML private TextField limiteJournaliereField;
    @FXML private ComboBox<String> statutComboBox;
    @FXML private ComboBox<Client> clientComboBox;

    @FXML private Label numeroError;
    @FXML private Label dateExpirationError;
    @FXML private Label cvvError;
    @FXML private Label soldeError;
    @FXML private Label limiteJournaliereError;
    @FXML private Label statutError;
    @FXML private Label clientError;

    private CarteBancaire carteBancaire;

    @FXML
    public void initialize() {
        statutComboBox.getItems().addAll("ACTIVE", "INACTIVE", "BLOQUEE");
    }

    public void setCarteBancaire(CarteBancaire carteBancaire) {
        this.carteBancaire = carteBancaire;
        if (carteBancaire.getId() != null) {
            numeroField.setText(carteBancaire.getNumero());
            dateExpirationPicker.setValue(carteBancaire.getDateExpiration());
            cvvField.setText(carteBancaire.getCvv());
            soldeField.setText(String.valueOf(carteBancaire.getSolde()));
            limiteJournaliereField.setText(String.valueOf(carteBancaire.getLimiteJournaliere()));
            statutComboBox.setValue(carteBancaire.getStatut());
            clientComboBox.setValue(carteBancaire.getClient());
        }
    }

    public void setClients(List<Client> clients) {
        clientComboBox.getItems().setAll(clients);
    }

    public CarteBancaire getCarteBancaire() {
        return carteBancaire;
    }

    @FXML
    private void handleOk() {
        if (validateInput()) {
            if (carteBancaire == null) {
                carteBancaire = new CarteBancaire();
            }
            carteBancaire.setNumero(numeroField.getText());
            carteBancaire.setDateExpiration(dateExpirationPicker.getValue());
            carteBancaire.setCvv(cvvField.getText());
            carteBancaire.setSolde(Double.parseDouble(soldeField.getText()));
            carteBancaire.setLimiteJournaliere(Double.parseDouble(limiteJournaliereField.getText()));
            carteBancaire.setStatut(statutComboBox.getValue());
            carteBancaire.setClient(clientComboBox.getValue());

            Stage stage = (Stage) numeroField.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) numeroField.getScene().getWindow();
        stage.close();
    }

    private boolean validateInput() {
        boolean isValid = true;

        if (numeroField.getText().trim().isEmpty()) {
            numeroError.setVisible(true);
            isValid = false;
        } else {
            numeroError.setVisible(false);
        }

        if (dateExpirationPicker.getValue() == null) {
            dateExpirationError.setVisible(true);
            isValid = false;
        } else {
            dateExpirationError.setVisible(false);
        }

        if (cvvField.getText().trim().isEmpty()) {
            cvvError.setVisible(true);
            isValid = false;
        } else {
            cvvError.setVisible(false);
        }

        try {
            Double.parseDouble(soldeField.getText());
            soldeError.setVisible(false);
        } catch (NumberFormatException e) {
            soldeError.setVisible(true);
            isValid = false;
        }

        try {
            Double.parseDouble(limiteJournaliereField.getText());
            limiteJournaliereError.setVisible(false);
        } catch (NumberFormatException e) {
            limiteJournaliereError.setVisible(true);
            isValid = false;
        }

        if (statutComboBox.getValue() == null) {
            statutError.setVisible(true);
            isValid = false;
        } else {
            statutError.setVisible(false);
        }

        if (clientComboBox.getValue() == null) {
            clientError.setVisible(true);
            isValid = false;
        } else {
            clientError.setVisible(false);
        }

        return isValid;
    }
} 