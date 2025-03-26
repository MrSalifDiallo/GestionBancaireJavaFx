# Documentation de l'Application de Gestion Bancaire

## ⚠️ Fonctionnalités Non Implémentées
Les vues suivantes sont présentes dans l'application mais ne sont pas encore fonctionnelles :
- **Gestion des Transactions** (`TransactionView.fxml`) - Pour gérer les transactions bancaires
- **Gestion des Abonnements** (`AbonnementView.fxml`) - Pour gérer les abonnements des clients
- **Gestion des Litiges** (`LitigeView.fxml`) - Pour gérer les réclamations et litiges
- **Programme de Fidélité** (`FideliteView.fxml`) - Pour gérer les points de fidélité des clients

Ces fonctionnalités sont prévues pour les futures versions de l'application.

### Aperçu des Vues Non Implémentées

#### 1. Gestion des Transactions (`TransactionView.fxml`)
- **Objectif** : Permettre le suivi et la gestion des transactions bancaires
- **Fonctionnalités prévues** :
  - Liste des transactions avec filtres (date, montant, type)
  - Détails des transactions (émetteur, destinataire, montant, date)
  - Historique des transactions par client
  - Génération de relevés de compte

#### 2. Gestion des Abonnements (`AbonnementView.fxml`)
- **Objectif** : Gérer les différents types d'abonnements bancaires
- **Fonctionnalités prévues** :
  - Création et modification d'abonnements
  - Suivi des paiements récurrents
  - Gestion des options d'abonnement
  - Notifications de renouvellement

#### 3. Gestion des Litiges (`LitigeView.fxml`)
- **Objectif** : Traiter les réclamations et litiges des clients
- **Fonctionnalités prévues** :
  - Enregistrement des réclamations
  - Suivi du statut des litiges
  - Communication avec les clients
  - Résolution des contestations de transactions

#### 4. Programme de Fidélité (`FideliteView.fxml`)
- **Objectif** : Gérer le système de récompenses des clients
- **Fonctionnalités prévues** :
  - Calcul des points de fidélité
  - Catalogue des récompenses
  - Historique des points gagnés/utilisés
  - Gestion des offres spéciales

## Table des matières
1. [Introduction](#introduction)
2. [Structure du Projet](#structure-du-projet)
3. [Processus de Connexion](#processus-de-connexion)
4. [Gestion des Clients](#gestion-des-clients)
5. [Gestion des Cartes Bancaires](#gestion-des-cartes-bancaires)
6. [Bibliothèques Utilisées](#bibliothèques-utilisées)

## Introduction
L'application de Gestion Bancaire est une solution complète permettant de gérer les clients, les cartes bancaires, et les transactions bancaires. Elle est développée en Java avec JavaFX pour l'interface utilisateur et utilise JPA (Java Persistence API) pour la persistance des données.

## Structure du Projet

### Organisation des Packages
```
com.groupeisi.gestionbancaires/
├── entities/
│   ├── Admin.java
│   ├── Client.java
│   └── CarteBancaire.java
├── services/
│   └── JpaUtils.java
└── views/
    ├── MainView.fxml
    ├── MainViewController.java
    ├── ClientView.fxml
    ├── ClientViewController.java
    ├── CarteBancaireView.fxml
    ├── CarteBancaireViewController.java
    ├── TransactionView.fxml          (Non implémenté)
    ├── AbonnementView.fxml           (Non implémenté)
    ├── LitigeView.fxml               (Non implémenté)
    └── FideliteView.fxml             (Non implémenté)
```

## Processus de Connexion

### 1. Page de Connexion
- **Fichier**: `LoginView.fxml`
- **Contrôleur**: `LoginViewController.java`
- **Champs**:
  - Username (TextField)
  - Password (PasswordField)
  - Bouton de connexion

### 2. Authentification
```java
@FXML
private void handleLogin() {
    String username = usernameField.getText();
    String password = passwordField.getText();
    
    EntityManager em = JpaUtils.getEm();
    try {
        Admin admin = em.createQuery("SELECT a FROM Admin a WHERE a.username = :username", Admin.class)
            .setParameter("username", username)
            .getSingleResult();
            
        if (admin != null && admin.getPassword().equals(password)) {
            // Connexion réussie
            loadMainView();
        }
    } catch (Exception e) {
        // Gestion des erreurs
    }
}
```

## Gestion des Clients

### 1. Vue Client (`ClientView.fxml`)
- **Interface principale**:
  - TableView pour la liste des clients
  - Formulaire de détails client
  - Boutons d'actions (Nouveau, Modifier, Supprimer)

### 2. Contrôleur Client (`ClientViewController.java`)
- **Fonctionnalités principales**:
  - Chargement des clients
  - Recherche de clients
  - Ajout/Modification/Suppression de clients
  - Validation des données

## Gestion des Cartes Bancaires

### 1. Vue Carte (`CarteBancaireView.fxml`)
- **Composants principaux**:
  - TableView des cartes
  - Vue détaillée de la carte
  - Boutons d'actions

### 2. Éditeur de Carte (`CarteBancaireEditorDialog.fxml`)
- **Champs du formulaire**:
  - Sélection du client
  - Date d'expiration
  - Statut de la carte
  - Bouton de génération de numéro

### 3. Contrôleur de Carte (`CarteBancaireViewController.java`)
- **Méthodes principales**:
```java
@FXML private void handleNouvelleCarte()
@FXML private void handleModifierCarte()
@FXML private void handleBloquerCarte()
private void loadCartes()
private void updateCarteDetails()
```

## Bibliothèques Utilisées

### 1. JavaFX
- **Importance**: Framework d'interface utilisateur
- **Composants utilisés**:
  - Scene et Stage pour la gestion des fenêtres
  - FXML pour la définition des vues
  - Controls (TableView, Button, TextField, etc.)

### 2. JPA (Java Persistence API)
- **Importance**: Persistance des données
- **Composants clés**:
  - EntityManager pour la gestion des entités
  - Queries pour les requêtes de base de données
  - Transactions pour la cohérence des données

### 3. Hibernate
- **Importance**: Implémentation de JPA
- **Fonctionnalités**:
  - Mapping objet-relationnel
  - Gestion des sessions
  - Cache de second niveau

## Styles et Thèmes

### 1. Feuilles de Style CSS
- **Fichier**: `styles/card-styles.css`
- **Éléments stylisés**:
  - Carte bancaire (dégradé, ombres)
  - Boutons et formulaires
  - Labels et textes

### 2. Thème Principal
```css
.card-preview {
    -fx-background-color: transparent;
}

.card-background {
    -fx-fill: linear-gradient(to right bottom, #1a237e, #3949ab);
    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 3);
}
```

## Sécurité et Validation

### 1. Validation des Données
- Vérification des champs obligatoires
- Validation des formats (email, téléphone)
- Messages d'erreur personnalisés

### 2. Sécurité
- Authentification des utilisateurs
- Gestion des sessions
- Protection des données sensibles

## Conclusion
Cette application offre une solution complète pour la gestion bancaire, avec une interface utilisateur moderne et intuitive, une gestion robuste des données, et des fonctionnalités de sécurité appropriées. 