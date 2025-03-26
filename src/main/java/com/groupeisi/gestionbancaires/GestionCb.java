package com.groupeisi.gestionbancaires;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GestionCb extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(GestionCb.class.getResource("/com/groupeisi/gestionbancaires/views/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        
        // Charger les styles CSS
        var cssUrl = getClass().getResource("/com/groupeisi/gestionbancaires/styles/styles.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }
        
        stage.setTitle("Gestion Bancaire");
        stage.setScene(scene);
        stage.setMaximized(true); // Ouvre en plein écran
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
} 