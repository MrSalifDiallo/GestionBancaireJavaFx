package com.groupeisi.gestionbancaires.views;

import com.groupeisi.gestionbancaires.entities.*;
import com.groupeisi.gestionbancaires.entities.Transaction.TypeTransaction;
import com.groupeisi.gestionbancaires.entities.Transaction.StatutTransaction;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import java.time.LocalDate;
import java.util.List;

public class TransactionViewController {
    @FXML private TableView<Transaction> transactionTable;
    @FXML private TableColumn<Transaction, String> dateColumn;
    @FXML private TableColumn<Transaction, Double> montantColumn;
    @FXML private TableColumn<Transaction, String> typeColumn;
    @FXML private TableColumn<Transaction, String> statutColumn;
    @FXML private TableColumn<Transaction, String> carteColumn;

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
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions.setAll(transactions);
    }

    @FXML
    private void handleValiderTransaction() {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            selectedTransaction.setStatutTransaction(StatutTransaction.VALIDEE);
            // Mettre à jour la base de données
        }
    }

    @FXML
    private void handleRejeterTransaction() {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            selectedTransaction.setStatutTransaction(StatutTransaction.REJETEE);
            // Mettre à jour la base de données
        }
    }
} 