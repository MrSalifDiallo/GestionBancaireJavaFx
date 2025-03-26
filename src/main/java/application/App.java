package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        try {
            // Charger la vue de connexion
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/LoginView.fxml"));
            AnchorPane loginView = loader.load();

            // Créer la scène
            Scene scene = new Scene(loginView);
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

            // Configurer la fenêtre
            stage.setTitle("Gestion Bancaire - Connexion");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
} 