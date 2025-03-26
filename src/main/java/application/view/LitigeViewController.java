package application.view;

import com.groupeisi.gestionbancaires.entities.*;
import com.groupeisi.gestionbancaires.entities.Transaction.StatutTransaction;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Date;
import java.time.LocalDate;
import java.util.List;

public class LitigeViewController {
    @FXML
    private TableView<Transaction> transactionTable;
    @FXML
    private TableColumn<Transaction, String> dateColumn;
    @FXML
    private TableColumn<Transaction, Double> montantColumn;
    @FXML
    private TableColumn<Transaction, String> typeColumn;
    @FXML
    private TableColumn<Transaction, String> statutColumn;
    @FXML
    private TableColumn<Transaction, String> carteColumn;

    @FXML
    private ComboBox<String> comboStatut;
    @FXML
    private TextField txtRecherche;
    @FXML
    private Label lblClient;
    @FXML
    private Label lblTransaction;
    @FXML
    private Label lblMontant;
    @FXML
    private Label lblDate;
    @FXML
    private TextArea txtMotif;
    @FXML
    private ComboBox<String> comboLitigeStatut;
    @FXML
    private TextArea txtResolution;

    private Transaction selectedTransaction;
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        dateColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDate().toString()));
        montantColumn.setCellValueFactory(cellData -> 
            new SimpleDoubleProperty(cellData.getValue().getMontant()).asObject());
        typeColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getTypeTransaction().toString()));
        statutColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getStatutTransaction().toString()));
        carteColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getCarteBancaire().getNumero()));

        transactionTable.setItems(transactions);
        
        comboStatut.getItems().addAll("TOUS", "EN_COURS", "RESOLU", "REJETE");
        comboStatut.setValue("TOUS");
        
        comboLitigeStatut.getItems().addAll("EN_ATTENTE", "VALIDEE", "REJETEE", "ANNULEE");
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions.setAll(transactions);
    }

    @FXML
    private void handleValiderTransaction() {
        selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            selectedTransaction.setStatutTransaction(StatutTransaction.VALIDEE);
            // Mettre à jour la base de données
            updateDetails();
        }
    }

    @FXML
    private void handleRejeterTransaction() {
        selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            selectedTransaction.setStatutTransaction(StatutTransaction.REJETEE);
            // Mettre à jour la base de données
            updateDetails();
        }
    }

    @FXML
    private void handleEnregistrer() {
        if (selectedTransaction != null) {
            // TODO: Sauvegarder les modifications dans la base de données
            updateDetails();
        }
    }

    private void updateDetails() {
        if (selectedTransaction != null) {
            lblClient.setText(selectedTransaction.getCarteBancaire().getClient().getNom() + " " +
                            selectedTransaction.getCarteBancaire().getClient().getPrenom());
            lblTransaction.setText(String.valueOf(selectedTransaction.getId()));
            lblMontant.setText(String.valueOf(selectedTransaction.getMontant()));
            lblDate.setText(selectedTransaction.getDate().toString());
            comboLitigeStatut.setValue(selectedTransaction.getStatutTransaction().toString());
        }
    }

    private void filterTransactions() {
        // TODO: Implémenter le filtrage des transactions
    }
} 