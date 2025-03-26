package com.groupeisi.gestionbancaires.views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.groupeisi.gestionbancaires.entities.*;

public class DashboardViewController {
    @FXML private Label totalClientsLabel;
    @FXML private Label totalCartesLabel;
    @FXML private Label totalTransactionsLabel;
    @FXML private Label totalLitigesLabel;

    @FXML private TableView<Transaction> transactionsTable;
    @FXML private TableColumn<Transaction, String> dateColumn;
    @FXML private TableColumn<Transaction, Double> montantColumn;
    @FXML private TableColumn<Transaction, String> typeColumn;
    @FXML private TableColumn<Transaction, String> statutColumn;
    @FXML private TableColumn<Transaction, String> clientColumn;

    @FXML private TableView<Litige> litigesTable;
    @FXML private TableColumn<Litige, String> litigeDateColumn;
    @FXML private TableColumn<Litige, String> litigeMotifColumn;
    @FXML private TableColumn<Litige, String> litigeStatutColumn;
    @FXML private TableColumn<Litige, String> litigeClientColumn;

    @FXML
    public void initialize() {
        // Configuration des colonnes du tableau des transactions
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        clientColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getCarteBancaire().getClient().getNom() + " " +
                cellData.getValue().getCarteBancaire().getClient().getPrenom()
            )
        );

        // Configuration des colonnes du tableau des litiges
        litigeDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateSignalement"));
        litigeMotifColumn.setCellValueFactory(new PropertyValueFactory<>("motif"));
        litigeStatutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        litigeClientColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getTransaction().getCarteBancaire().getClient().getNom() + " " +
                cellData.getValue().getTransaction().getCarteBancaire().getClient().getPrenom()
            )
        );

        // Charger les données
        loadData();
    }

    private void loadData() {
        // TODO: Charger les données depuis la base de données
        // Exemple :
        // List<Transaction> transactions = transactionService.findLatestTransactions();
        // transactionsTable.getItems().setAll(transactions);
        
        // List<Litige> litiges = litigeService.findRecentLitiges();
        // litigesTable.getItems().setAll(litiges);
        
        // Mettre à jour les statistiques
        // totalClientsLabel.setText(String.valueOf(clientService.countAll()));
        // totalCartesLabel.setText(String.valueOf(carteBancaireService.countActive()));
        // totalTransactionsLabel.setText(String.valueOf(transactionService.countToday()));
        // totalLitigesLabel.setText(String.valueOf(litigeService.countPending()));
    }
} 