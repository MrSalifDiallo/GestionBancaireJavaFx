package com.groupeisi.gestionbancaires.views;

import com.groupeisi.gestionbancaires.entities.CarteBancaire;
import com.groupeisi.gestionbancaires.services.JpaUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

public class CarteBancaireViewController {
    @FXML private TableView<CarteBancaire> carteTable;
    @FXML private TableColumn<CarteBancaire, String> colNumero;
    @FXML private TableColumn<CarteBancaire, String> colClient;
    @FXML private TableColumn<CarteBancaire, String> colDateExpiration;
    @FXML private TableColumn<CarteBancaire, String> colStatut;
    @FXML private TableColumn<CarteBancaire, Void> colActions;
    
    @FXML private TextField searchField;
    @FXML private Label labelNumero;
    @FXML private Label labelClient;
    @FXML private Label labelDateExpiration;
    @FXML private Label labelCvv;
    @FXML private Label labelStatut;

    private ObservableList<CarteBancaire> cartes;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");

    @FXML
    private void initialize() {
        initializeColumns();
        loadCartes();
        setupSearch();
        setupTableSelection();
    }

    private void initializeColumns() {
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colClient.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getClient().getNom() + " " + 
                cellData.getValue().getClient().getPrenom()
            )
        );
        colDateExpiration.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                dateFormat.format(cellData.getValue().getDateExpiration())
            )
        );
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
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
            cartes = FXCollections.observableArrayList(cartesList);
            carteTable.setItems(cartes);
        } catch (Exception e) {
            showError("Erreur lors du chargement des cartes", e.getMessage());
        } finally {
            if (em != null) em.close();
        }
    }

    private void setupSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                carteTable.setItems(cartes);
            } else {
                ObservableList<CarteBancaire> filteredList = cartes.filtered(carte ->
                    carte.getNumero().toLowerCase().contains(newValue.toLowerCase()) ||
                    carte.getClient().getNom().toLowerCase().contains(newValue.toLowerCase()) ||
                    carte.getClient().getPrenom().toLowerCase().contains(newValue.toLowerCase())
                );
                carteTable.setItems(filteredList);
            }
        });
    }

    private void setupTableSelection() {
        carteTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> updateCarteDetails(newValue)
        );
    }

    private void updateCarteDetails(CarteBancaire carte) {
        if (carte != null) {
            try {
                // Charger la vue détaillée de la carte
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupeisi/gestionbancaires/views/CarteBancaireDetailView.fxml"));
                VBox cardView = loader.load();
                
                // Mettre à jour les labels dans la vue détaillée
                Label cardNumberLabel = (Label) cardView.lookup("#cardNumberPreview");
                Label cardHolderLabel = (Label) cardView.lookup("#cardHolderPreview");
                Label cardExpiryLabel = (Label) cardView.lookup("#cardExpiryPreview");
                Label cardCvvLabel = (Label) cardView.lookup("#cardCvvPreview");
                
                cardNumberLabel.setText(carte.getNumero());
                cardHolderLabel.setText(carte.getClient().getNom() + " " + carte.getClient().getPrenom());
                cardExpiryLabel.setText(dateFormat.format(carte.getDateExpiration()));
                cardCvvLabel.setText(carte.getCvv());
                
                // Remplacer les labels existants par la vue détaillée
                VBox detailsContainer = (VBox) labelNumero.getParent();
                detailsContainer.getChildren().clear();
                detailsContainer.getChildren().add(cardView);
                
            } catch (IOException e) {
                showError("Erreur", "Impossible de charger la vue détaillée de la carte");
            }
        }
    }

    @FXML
    private void handleNouvelleCarte() {
        try {
            // Charger le dialogue
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupeisi/gestionbancaires/views/CarteBancaireEditorDialog.fxml"));
            DialogPane dialogPane = loader.load();
            
            // Créer le dialogue
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Nouvelle Carte Bancaire");
            
            // Obtenir le contrôleur et initialiser
            CarteBancaireEditorDialogController controller = loader.getController();
            controller.setDialogPane(dialogPane);
            controller.setCarteBancaire(null); // Mode création
            
            // Afficher le dialogue et traiter le résultat
            Optional<ButtonType> clickedButton = dialog.showAndWait();
            
            if (clickedButton.isPresent() && clickedButton.get() == ButtonType.OK) {
                CarteBancaire nouvelleCarte = controller.getCarteBancaire();
                EntityManager em = null;
                try {
                    em = JpaUtils.getEm();
                    em.getTransaction().begin();
                    em.persist(nouvelleCarte);
                    em.getTransaction().commit();
                    loadCartes(); // Recharger la liste
                    showInfo("Succès", "La carte a été créée avec succès");
                } catch (Exception e) {
                    if (em != null && em.getTransaction().isActive()) {
                        em.getTransaction().rollback();
                    }
                    showError("Erreur", "Impossible de créer la carte : " + e.getMessage());
                } finally {
                    if (em != null) em.close();
                }
            }
        } catch (IOException e) {
            showError("Erreur", "Impossible d'ouvrir le dialogue : " + e.getMessage());
        }
    }

    @FXML
    private void handleModifierCarte() {
        CarteBancaire selectedCarte = carteTable.getSelectionModel().getSelectedItem();
        if (selectedCarte == null) {
            showWarning("Aucune sélection", "Veuillez sélectionner une carte à modifier");
            return;
        }

        try {
            // Charger le dialogue
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupeisi/gestionbancaires/views/CarteBancaireEditorDialog.fxml"));
            DialogPane dialogPane = loader.load();
            
            // Créer le dialogue
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Modifier la Carte Bancaire");
            
            // Obtenir le contrôleur et initialiser
            CarteBancaireEditorDialogController controller = loader.getController();
            controller.setDialogPane(dialogPane);
            controller.setCarteBancaire(selectedCarte); // Mode édition
            
            // Afficher le dialogue et traiter le résultat
            Optional<ButtonType> clickedButton = dialog.showAndWait();
            
            if (clickedButton.isPresent() && clickedButton.get() == ButtonType.OK) {
                CarteBancaire carteModifiee = controller.getCarteBancaire();
                EntityManager em = null;
                try {
                    em = JpaUtils.getEm();
                    em.getTransaction().begin();
                    em.merge(carteModifiee);
                    em.getTransaction().commit();
                    loadCartes(); // Recharger la liste
                    showInfo("Succès", "La carte a été modifiée avec succès");
                } catch (Exception e) {
                    if (em != null && em.getTransaction().isActive()) {
                        em.getTransaction().rollback();
                    }
                    showError("Erreur", "Impossible de modifier la carte : " + e.getMessage());
                } finally {
                    if (em != null) em.close();
                }
            }
        } catch (IOException e) {
            showError("Erreur", "Impossible d'ouvrir le dialogue : " + e.getMessage());
        }
    }

    @FXML
    private void handleBloquerCarte() {
        CarteBancaire selectedCarte = carteTable.getSelectionModel().getSelectedItem();
        if (selectedCarte == null) {
            showWarning("Aucune sélection", "Veuillez sélectionner une carte à bloquer");
            return;
        }

        if (showConfirmation("Bloquer la carte", 
            "Êtes-vous sûr de vouloir bloquer la carte " + selectedCarte.getNumero() + " ?")) {
            EntityManager em = null;
            try {
                em = JpaUtils.getEm();
                em.getTransaction().begin();
                selectedCarte = em.merge(selectedCarte);
                selectedCarte.setStatut("BLOQUEE");
                em.getTransaction().commit();
                loadCartes(); // Recharger la liste
                showInfo("Succès", "La carte a été bloquée avec succès");
            } catch (Exception e) {
                if (em != null && em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                showError("Erreur", "Impossible de bloquer la carte : " + e.getMessage());
            } finally {
                if (em != null) em.close();
            }
        }
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showWarning(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean showConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
} 