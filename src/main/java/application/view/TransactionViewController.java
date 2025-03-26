package application.view;

import com.groupeisi.gestionbancaires.entities.Transaction;
import com.groupeisi.gestionbancaires.entities.Transaction.TypeTransaction;
import com.groupeisi.gestionbancaires.entities.Transaction.StatutTransaction;
import com.groupeisi.gestionbancaires.entities.AuthentificationTransaction;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.Date;

public class TransactionViewController {
    @FXML
    private TableView<Transaction> tableTransactions;
    @FXML
    private TableColumn<Transaction, Date> colDate;
    @FXML
    private TableColumn<Transaction, String> colType;
    @FXML
    private TableColumn<Transaction, String> colCarte;
    @FXML
    private TableColumn<Transaction, Double> colMontant;
    @FXML
    private TableColumn<Transaction, String> colCommercant;
    @FXML
    private TableColumn<Transaction, String> colStatut;
    @FXML
    private TableColumn<Transaction, String> colAuthentification;

    @FXML
    private TableView<Transaction> tableHistorique;
    @FXML
    private TableColumn<Transaction, Date> colHistDate;
    @FXML
    private TableColumn<Transaction, String> colHistType;
    @FXML
    private TableColumn<Transaction, String> colHistCarte;
    @FXML
    private TableColumn<Transaction, Double> colHistMontant;
    @FXML
    private TableColumn<Transaction, String> colHistCommercant;
    @FXML
    private TableColumn<Transaction, String> colHistStatut;

    @FXML
    private DatePicker dateDebut;
    @FXML
    private DatePicker dateFin;
    @FXML
    private ComboBox<String> comboFiltre;
    @FXML
    private TextField txtRecherche;
    @FXML
    private Label lblStatut;
    @FXML
    private TextField txtOTP;
    @FXML
    private Button btnValiderOTP;

    private Transaction selectedTransaction;
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    private ObservableList<Transaction> historique = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        initializeColumns();
        initializeListeners();
        loadTransactions();
        
        comboFiltre.getItems().addAll("PAIEMENT", "RETRAIT", "TOUS");
        comboFiltre.setValue("TOUS");
    }

    private void initializeColumns() {
        // Configuration des colonnes de la table des transactions en cours
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colType.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getTypeTransaction().toString()));
        colCarte.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getCarteBancaire().getNumero()));
        colMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        colCommercant.setCellValueFactory(new PropertyValueFactory<>("commercant"));
        colStatut.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getStatutTransaction().toString()));
        colAuthentification.setCellValueFactory(cellData -> {
            AuthentificationTransaction auth = cellData.getValue().getAuthentification();
            return new SimpleStringProperty(auth != null ? auth.getStatut() : "N/A");
        });

        // Configuration des colonnes de la table historique
        colHistDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colHistType.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getTypeTransaction().toString()));
        colHistCarte.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getCarteBancaire().getNumero()));
        colHistMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        colHistCommercant.setCellValueFactory(new PropertyValueFactory<>("commercant"));
        colHistStatut.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getStatutTransaction().toString()));
    }

    private void initializeListeners() {
        tableTransactions.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                selectedTransaction = newValue;
                if (selectedTransaction != null) {
                    updateDetails();
                }
            }
        );

        comboFiltre.valueProperty().addListener((observable, oldValue, newValue) -> {
            filterTransactions();
        });

        txtRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTransactions();
        });
    }

    @FXML
    private void handleNouvelleTransaction() {
        // TODO: Ouvrir la fenêtre de création de transaction
    }

    @FXML
    private void handleValiderOTP() {
        if (selectedTransaction != null && txtOTP.getText() != null && !txtOTP.getText().isEmpty()) {
            // TODO: Valider l'OTP avec le service
            updateDetails();
        }
    }

    @FXML
    private void handleSignalerLitige() {
        if (selectedTransaction != null) {
            // TODO: Ouvrir la fenêtre de signalement de litige
        }
    }

    @FXML
    private void handleAnnulerTransaction() {
        if (selectedTransaction != null) {
            // TODO: Ajouter une confirmation
            // TODO: Appeler le service pour annuler la transaction
            transactions.remove(selectedTransaction);
        }
    }

    @FXML
    private void handleFiltrer() {
        if (dateDebut.getValue() != null && dateFin.getValue() != null) {
            // TODO: Charger l'historique filtré par dates
            loadHistorique();
        }
    }

    private void loadTransactions() {
        // TODO: Charger les transactions depuis le service
        tableTransactions.setItems(transactions);
    }

    private void loadHistorique() {
        // TODO: Charger l'historique depuis le service
        tableHistorique.setItems(historique);
    }

    private void updateDetails() {
        if (selectedTransaction != null) {
            lblStatut.setText(selectedTransaction.getStatutTransaction().toString());
            // Activer/désactiver les contrôles selon le statut
            boolean isEnCours = StatutTransaction.EN_ATTENTE.equals(selectedTransaction.getStatutTransaction());
            txtOTP.setDisable(!isEnCours);
            btnValiderOTP.setDisable(!isEnCours);
        }
    }

    private void filterTransactions() {
        // TODO: Implémenter le filtrage des transactions
    }
} 