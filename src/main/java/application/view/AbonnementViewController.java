package application.view;

import com.groupeisi.gestionbancaires.entities.Abonnement;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.Date;

public class AbonnementViewController {
    @FXML
    private TableView<Abonnement> tableAbonnements;
    @FXML
    private TableColumn<Abonnement, String> colClient;
    @FXML
    private TableColumn<Abonnement, String> colCarte;
    @FXML
    private TableColumn<Abonnement, String> colService;
    @FXML
    private TableColumn<Abonnement, Double> colMontant;
    @FXML
    private TableColumn<Abonnement, Date> colDateProchainPrel;
    @FXML
    private TableColumn<Abonnement, String> colStatut;

    @FXML
    private TableView<Abonnement> tableHistorique;
    @FXML
    private TableColumn<Abonnement, String> colHistClient;
    @FXML
    private TableColumn<Abonnement, String> colHistService;
    @FXML
    private TableColumn<Abonnement, Double> colHistMontant;
    @FXML
    private TableColumn<Abonnement, Date> colHistDateDebut;
    @FXML
    private TableColumn<Abonnement, Date> colHistDateFin;
    @FXML
    private TableColumn<Abonnement, String> colHistStatut;

    @FXML
    private Button btnNouvelAbonnement;
    @FXML
    private ComboBox<String> comboService;
    @FXML
    private TextField txtRecherche;
    @FXML
    private DatePicker dateDebut;
    @FXML
    private DatePicker dateFin;
    @FXML
    private Label lblClient;
    @FXML
    private Label lblService;
    @FXML
    private Label lblMontant;
    @FXML
    private Label lblProchainPrel;
    @FXML
    private ComboBox<String> comboStatut;

    private Abonnement selectedAbonnement;
    private ObservableList<Abonnement> abonnements = FXCollections.observableArrayList();
    private ObservableList<Abonnement> historique = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        initializeColumns();
        initializeListeners();
        loadAbonnements();
        
        comboService.getItems().addAll("Netflix", "Spotify", "Amazon Prime", "Disney+");
        comboStatut.getItems().addAll("ACTIF", "SUSPENDU", "ANNULE");
    }

    private void initializeColumns() {
        // Configuration des colonnes de la table des abonnements actifs
        colClient.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getCarte().getClient().getNom() + " " +
                                   cellData.getValue().getCarte().getClient().getPrenom()));
        colCarte.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getCarte().getNumero()));
        colService.setCellValueFactory(new PropertyValueFactory<>("service"));
        colMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        colDateProchainPrel.setCellValueFactory(new PropertyValueFactory<>("datePrelevement"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        // Configuration des colonnes de la table historique
        colHistClient.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getCarte().getClient().getNom() + " " +
                                   cellData.getValue().getCarte().getClient().getPrenom()));
        colHistService.setCellValueFactory(new PropertyValueFactory<>("service"));
        colHistMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        colHistDateDebut.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        colHistDateFin.setCellValueFactory(new PropertyValueFactory<>("datePrelevement"));
        colHistStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
    }

    private void initializeListeners() {
        tableAbonnements.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                selectedAbonnement = newValue;
                if (selectedAbonnement != null) {
                    updateDetails();
                }
            }
        );

        comboService.valueProperty().addListener((observable, oldValue, newValue) -> {
            filterAbonnements();
        });

        txtRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filterAbonnements();
        });
    }

    @FXML
    private void handleNouvelAbonnement() {
        // TODO: Ouvrir la fenêtre de création d'abonnement
    }

    @FXML
    private void handleModifier() {
        if (selectedAbonnement != null) {
            // TODO: Ouvrir la fenêtre de modification d'abonnement
        }
    }

    @FXML
    private void handleSuspendre() {
        if (selectedAbonnement != null) {
            selectedAbonnement.setStatut("SUSPENDU");
            // TODO: Mettre à jour l'abonnement dans la base de données
            updateDetails();
        }
    }

    @FXML
    private void handleResilier() {
        if (selectedAbonnement != null) {
            // TODO: Ajouter une confirmation
            selectedAbonnement.setStatut("ANNULE");
            // TODO: Mettre à jour l'abonnement dans la base de données
            updateDetails();
        }
    }

    @FXML
    private void handleFiltrer() {
        if (dateDebut.getValue() != null && dateFin.getValue() != null) {
            loadHistorique();
        }
    }

    private void loadAbonnements() {
        // TODO: Charger les abonnements depuis le service
        tableAbonnements.setItems(abonnements);
    }

    private void loadHistorique() {
        // TODO: Charger l'historique depuis le service avec les dates sélectionnées
        tableHistorique.setItems(historique);
    }

    private void updateDetails() {
        if (selectedAbonnement != null) {
            lblClient.setText(selectedAbonnement.getCarte().getClient().getNom() + " " +
                            selectedAbonnement.getCarte().getClient().getPrenom());
            lblService.setText(selectedAbonnement.getService());
            lblMontant.setText(String.format("%.2f €", selectedAbonnement.getMontant()));
            lblProchainPrel.setText(selectedAbonnement.getDatePrelevement().toString());
            comboStatut.setValue(selectedAbonnement.getStatut());
        }
    }

    private void filterAbonnements() {
        // TODO: Implémenter le filtrage des abonnements
    }
} 