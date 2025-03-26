package application.view;

import com.groupeisi.gestionbancaires.entities.CarteBancaire;
import com.groupeisi.gestionbancaires.entities.Transaction;
import com.groupeisi.gestionbancaires.entities.Abonnement;
import com.groupeisi.gestionbancaires.services.JpaUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Date;

public class CarteBancaireViewController {
    @FXML
    private TableView<CarteBancaire> carteTable;
    @FXML
    private TableColumn<CarteBancaire, Integer> idColumn;
    @FXML
    private TableColumn<CarteBancaire, String> numeroColumn;
    @FXML
    private TableColumn<CarteBancaire, LocalDate> dateExpirationColumn;
    @FXML
    private TableColumn<CarteBancaire, Double> soldeColumn;
    @FXML
    private TableColumn<CarteBancaire, Double> limiteJournaliereColumn;
    @FXML
    private TableColumn<CarteBancaire, String> statutColumn;
    @FXML
    private TableColumn<CarteBancaire, String> clientColumn;

    @FXML
    private TableView<Transaction> tableTransactions;
    @FXML
    private TableColumn<Transaction, Date> colTransDate;
    @FXML
    private TableColumn<Transaction, String> colTransType;
    @FXML
    private TableColumn<Transaction, Double> colTransMontant;
    @FXML
    private TableColumn<Transaction, String> colTransStatut;
    @FXML
    private TableColumn<Transaction, String> colTransCommercant;

    @FXML
    private TableView<Abonnement> tableAbonnements;
    @FXML
    private TableColumn<Abonnement, String> colAboService;
    @FXML
    private TableColumn<Abonnement, Double> colAboMontant;
    @FXML
    private TableColumn<Abonnement, Date> colAboDatePrel;
    @FXML
    private TableColumn<Abonnement, String> colAboStatut;

    @FXML
    private TextField txtRecherche;
    @FXML
    private ComboBox<String> comboStatut;
    @FXML
    private TextField txtLimite;
    @FXML
    private Button btnBloquer;
    @FXML
    private Button btnSupprimer;

    private CarteBancaire selectedCarte;
    private ObservableList<CarteBancaire> cartes = FXCollections.observableArrayList();
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    private ObservableList<Abonnement> abonnements = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));
        dateExpirationColumn.setCellValueFactory(new PropertyValueFactory<>("dateExpiration"));
        soldeColumn.setCellValueFactory(new PropertyValueFactory<>("solde"));
        limiteJournaliereColumn.setCellValueFactory(new PropertyValueFactory<>("limiteJournaliere"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        clientColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getClient().getNom() + " " + 
                                   cellData.getValue().getClient().getPrenom()));

        carteTable.setItems(cartes);

        initializeListeners();
        loadCartes();
        
        comboStatut.getItems().addAll("ACTIVE", "INACTIVE", "BLOQUEE");
    }

    private void initializeListeners() {
        carteTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                selectedCarte = newValue;
                if (selectedCarte != null) {
                    updateDetails();
                    loadTransactions();
                    loadAbonnements();
                }
            }
        );

        txtRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filterCartes();
        });
    }

    public void setCartes(List<CarteBancaire> cartes) {
        this.cartes.setAll(cartes);
    }

    @FXML
    private void handleAjouterCarte() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupeisi/gestionbancaires/views/CarteBancaireEditorDialog.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Nouvelle Carte Bancaire");
            stage.setScene(scene);

            CarteBancaireEditorDialogController controller = loader.getController();
            CarteBancaire newCarte = new CarteBancaire();
            controller.setCarteBancaire(newCarte);

            stage.showAndWait();
            if (newCarte.getId() != null) {
                cartes.add(newCarte);
            }
        } catch (Exception e) {
            showError("Erreur", "Impossible d'ouvrir la fenêtre d'édition : " + e.getMessage());
        }
    }

    @FXML
    private void handleModifierCarte() {
        CarteBancaire selectedCarte = carteTable.getSelectionModel().getSelectedItem();
        if (selectedCarte != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupeisi/gestionbancaires/views/CarteBancaireEditorDialog.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();
                stage.setTitle("Modifier Carte Bancaire");
                stage.setScene(scene);

                CarteBancaireEditorDialogController controller = loader.getController();
                controller.setCarteBancaire(selectedCarte);

                stage.showAndWait();
                carteTable.refresh();
            } catch (Exception e) {
                showError("Erreur", "Impossible d'ouvrir la fenêtre d'édition : " + e.getMessage());
            }
        } else {
            showError("Attention", "Veuillez sélectionner une carte à modifier");
        }
    }

    @FXML
    private void handleSupprimerCarte() {
        CarteBancaire selectedCarte = carteTable.getSelectionModel().getSelectedItem();
        if (selectedCarte != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Supprimer la carte");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer cette carte ?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                EntityManager em = null;
                try {
                    em = JpaUtils.getEm();
                    em.getTransaction().begin();
                    selectedCarte = em.merge(selectedCarte);
                    em.remove(selectedCarte);
                    em.getTransaction().commit();
                    cartes.remove(selectedCarte);
                    showInfo("Succès", "La carte a été supprimée avec succès");
                } catch (Exception e) {
                    if (em != null && em.getTransaction().isActive()) {
                        em.getTransaction().rollback();
                    }
                    showError("Erreur", "Impossible de supprimer la carte : " + e.getMessage());
                } finally {
                    if (em != null) em.close();
                }
            }
        } else {
            showError("Attention", "Veuillez sélectionner une carte à supprimer");
        }
    }

    private void loadCartes() {
        EntityManager em = null;
        try {
            em = JpaUtils.getEm();
            TypedQuery<CarteBancaire> query = em.createQuery(
                "SELECT c FROM CarteBancaire c JOIN FETCH c.client", 
                CarteBancaire.class
            );
            List<CarteBancaire> cartesList = query.getResultList();
            cartes.setAll(cartesList);
            carteTable.setItems(cartes);
        } catch (Exception e) {
            showError("Erreur", "Impossible de charger les cartes : " + e.getMessage());
        } finally {
            if (em != null) em.close();
        }
    }

    private void loadTransactions() {
        if (selectedCarte != null) {
            EntityManager em = null;
            try {
                em = JpaUtils.getEm();
                TypedQuery<Transaction> query = em.createQuery(
                    "SELECT t FROM Transaction t WHERE t.carteBancaire = :carte ORDER BY t.date DESC",
                    Transaction.class
                );
                query.setParameter("carte", selectedCarte);
                List<Transaction> transactionsList = query.getResultList();
                transactions.setAll(transactionsList);
                tableTransactions.setItems(transactions);
            } catch (Exception e) {
                showError("Erreur", "Impossible de charger les transactions : " + e.getMessage());
            } finally {
                if (em != null) em.close();
            }
        }
    }

    private void loadAbonnements() {
        if (selectedCarte != null) {
            EntityManager em = null;
            try {
                em = JpaUtils.getEm();
                TypedQuery<Abonnement> query = em.createQuery(
                    "SELECT a FROM Abonnement a WHERE a.carte = :carte ORDER BY a.datePrelevement DESC",
                    Abonnement.class
                );
                query.setParameter("carte", selectedCarte);
                List<Abonnement> abonnementsList = query.getResultList();
                abonnements.setAll(abonnementsList);
                tableAbonnements.setItems(abonnements);
            } catch (Exception e) {
                showError("Erreur", "Impossible de charger les abonnements : " + e.getMessage());
            } finally {
                if (em != null) em.close();
            }
        }
    }

    private void updateDetails() {
        if (selectedCarte != null) {
            comboStatut.setValue(selectedCarte.getStatut());
            txtLimite.setText(String.valueOf(selectedCarte.getLimiteJournaliere()));
        }
    }

    private void filterCartes() {
        String searchText = txtRecherche.getText().toLowerCase();
        if (searchText.isEmpty()) {
            carteTable.setItems(cartes);
            return;
        }

        ObservableList<CarteBancaire> filteredList = FXCollections.observableArrayList();
        for (CarteBancaire carte : cartes) {
            if (carte.getNumero().toLowerCase().contains(searchText) ||
                carte.getClient().getNom().toLowerCase().contains(searchText) ||
                carte.getClient().getPrenom().toLowerCase().contains(searchText)) {
                filteredList.add(carte);
            }
        }
        carteTable.setItems(filteredList);
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 