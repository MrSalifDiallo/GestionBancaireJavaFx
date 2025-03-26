package com.groupeisi.gestionbancaires;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import com.groupeisi.gestionbancaires.entities.Admin;
import com.groupeisi.gestionbancaires.services.JpaUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GestionCbController {
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblMessage;

    @FXML
    protected void onLoginButtonClick() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        EntityManager em = null;

        if (username.isEmpty() || password.isEmpty()) {
            lblMessage.setText("Veuillez remplir tous les champs");
            lblMessage.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            em = JpaUtils.getEm();
            System.out.println("Tentative de connexion pour l'utilisateur : " + username);
            
            em.getTransaction().begin();
            TypedQuery<Admin> query = em.createQuery(
                "SELECT a FROM Admin a WHERE a.username = :username AND a.password = :password",
                Admin.class);
            query.setParameter("username", username);
            query.setParameter("password", password);

            try {
                Admin admin = query.getSingleResult();
                System.out.println("Admin trouvé : " + admin.getUsername() + ", rôle : " + admin.getRole());
                em.getTransaction().commit();
                
                lblMessage.setText("Connexion réussie !");
                lblMessage.setStyle("-fx-text-fill: green;");
                
                // Charger la vue principale
                try {
                    System.out.println("Chargement de la vue principale...");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupeisi/gestionbancaires/MainView.fxml"));
                    BorderPane mainView = loader.load();
                    
                    // Créer une nouvelle scène
                    Scene scene = new Scene(mainView);
                    String cssPath = "/styles/styles.css";
                    System.out.println("Chargement du CSS : " + cssPath);
                    scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
                    
                    // Obtenir la fenêtre actuelle
                    Stage stage = (Stage) txtUsername.getScene().getWindow();
                    stage.setTitle("Gestion Bancaire - Administration");
                    stage.setScene(scene);
                    stage.setMaximized(true);
                    System.out.println("Vue principale chargée avec succès");
                } catch (Exception e) {
                    System.err.println("Erreur lors du chargement de la vue : " + e.getMessage());
                    e.printStackTrace();
                    lblMessage.setText("Erreur lors du chargement de l'application");
                    lblMessage.setStyle("-fx-text-fill: red;");
                }
            } catch (jakarta.persistence.NoResultException e) {
                System.out.println("Aucun admin trouvé avec ces identifiants");
                em.getTransaction().rollback();
                lblMessage.setText("Identifiants incorrects");
                lblMessage.setStyle("-fx-text-fill: red;");
            }
        } catch (Exception e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            lblMessage.setText("Erreur de connexion à la base de données : " + e.getMessage());
            lblMessage.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
} 