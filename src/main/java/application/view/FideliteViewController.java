package application.view;

import com.groupeisi.gestionbancaires.entities.Client;
import com.groupeisi.gestionbancaires.entities.Fidelite;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.Date;

public class FideliteViewController {
    @FXML
    private TableView<Client> tableClients;
    @FXML
    private TableColumn<Client, String> colNom;
    @FXML
    private TableColumn<Client, String> colPrenom;
    @FXML
    private TableColumn<Client, Integer> colPoints;

    @FXML
    private TextField txtRecherche;
    @FXML
    private Label lblClient;
    @FXML
    private Label lblPoints;
    @FXML
    private Label lblNiveau;
    @FXML
    private LineChart<String, Number> chartPoints;
    @FXML
    private ListView<String> listRecompenses;
    @FXML
    private TableView<EchangePoints> tableEchanges;
    @FXML
    private TableColumn<EchangePoints, Date> colDateEchange;
    @FXML
    private TableColumn<EchangePoints, String> colRecompense;
    @FXML
    private TableColumn<EchangePoints, Integer> colPointsUtilises;
    @FXML
    private Button btnEchanger;

    private Client selectedClient;
    private ObservableList<Client> clients = FXCollections.observableArrayList();
    private ObservableList<String> recompenses = FXCollections.observableArrayList();
    private ObservableList<EchangePoints> echanges = FXCollections.observableArrayList();

    // Classe interne pour représenter les échanges de points
    public static class EchangePoints {
        private Date date;
        private String recompense;
        private int pointsUtilises;

        public EchangePoints(Date date, String recompense, int pointsUtilises) {
            this.date = date;
            this.recompense = recompense;
            this.pointsUtilises = pointsUtilises;
        }

        public Date getDate() { return date; }
        public String getRecompense() { return recompense; }
        public int getPointsUtilises() { return pointsUtilises; }
    }

    @FXML
    private void initialize() {
        initializeColumns();
        initializeListeners();
        loadClients();
        initializeRecompenses();
        
        // Configuration du graphique
        chartPoints.setTitle("Évolution des points");
        chartPoints.getXAxis().setLabel("Mois");
        chartPoints.getYAxis().setLabel("Points");
    }

    private void initializeColumns() {
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colPoints.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty(cellData.getValue().getFidelite().getPointsAcquis()).asObject());

        colDateEchange.setCellValueFactory(new PropertyValueFactory<>("date"));
        colRecompense.setCellValueFactory(new PropertyValueFactory<>("recompense"));
        colPointsUtilises.setCellValueFactory(new PropertyValueFactory<>("pointsUtilises"));
    }

    private void initializeListeners() {
        tableClients.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                selectedClient = newValue;
                if (selectedClient != null) {
                    updateDetails();
                    updateChart();
                    loadEchanges();
                }
            }
        );

        txtRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filterClients();
        });

        listRecompenses.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                updateEchangeButtonState();
            }
        );
    }

    private void initializeRecompenses() {
        recompenses.addAll(
            "Cashback 10€ (1000 points)",
            "Bon d'achat 20€ (2000 points)",
            "Carte Premium (5000 points)",
            "Week-end VIP (10000 points)"
        );
        listRecompenses.setItems(recompenses);
    }

    @FXML
    private void handleEchangerPoints() {
        String selectedRecompense = listRecompenses.getSelectionModel().getSelectedItem();
        if (selectedClient != null && selectedRecompense != null) {
            int pointsNecessaires = extractPointsNecessaires(selectedRecompense);
            if (selectedClient.getFidelite().getPointsAcquis() >= pointsNecessaires) {
                // TODO: Effectuer l'échange de points
                selectedClient.getFidelite().setPointsAcquis(
                    selectedClient.getFidelite().getPointsAcquis() - pointsNecessaires
                );
                echanges.add(new EchangePoints(new Date(), selectedRecompense, pointsNecessaires));
                updateDetails();
            } else {
                // TODO: Afficher un message d'erreur
            }
        }
    }

    private void loadClients() {
        // TODO: Charger les clients depuis le service
        tableClients.setItems(clients);
    }

    private void loadEchanges() {
        if (selectedClient != null) {
            // TODO: Charger les échanges depuis le service
            tableEchanges.setItems(echanges);
        }
    }

    private void updateDetails() {
        if (selectedClient != null) {
            lblClient.setText(selectedClient.getNom() + " " + selectedClient.getPrenom());
            lblPoints.setText(String.valueOf(selectedClient.getFidelite().getPointsAcquis()));
            lblNiveau.setText(determinerNiveau(selectedClient.getFidelite().getPointsAcquis()));
        }
    }

    private void updateChart() {
        if (selectedClient != null) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Points");
            
            // TODO: Charger les données réelles depuis le service
            series.getData().add(new XYChart.Data<>("Jan", 100));
            series.getData().add(new XYChart.Data<>("Fév", 250));
            series.getData().add(new XYChart.Data<>("Mar", 400));
            // ...

            chartPoints.getData().clear();
            chartPoints.getData().add(series);
        }
    }

    private void updateEchangeButtonState() {
        String selectedRecompense = listRecompenses.getSelectionModel().getSelectedItem();
        if (selectedClient != null && selectedRecompense != null) {
            int pointsNecessaires = extractPointsNecessaires(selectedRecompense);
            btnEchanger.setDisable(selectedClient.getFidelite().getPointsAcquis() < pointsNecessaires);
        }
    }

    private void filterClients() {
        // TODO: Implémenter le filtrage des clients
    }

    private String determinerNiveau(int points) {
        if (points >= 10000) return "PLATINUM";
        if (points >= 5000) return "GOLD";
        if (points >= 2000) return "SILVER";
        return "BRONZE";
    }

    private int extractPointsNecessaires(String recompense) {
        // Format attendu: "Description (X points)"
        try {
            int debut = recompense.lastIndexOf("(") + 1;
            int fin = recompense.lastIndexOf(" points)");
            return Integer.parseInt(recompense.substring(debut, fin));
        } catch (Exception e) {
            return Integer.MAX_VALUE;
        }
    }
} 