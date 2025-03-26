# Application de Gestion Bancaire

Une application JavaFX pour la gestion des clients bancaires, des cartes bancaires, des transactions et des programmes de fidélité.

## Prérequis

- Java 17 ou supérieur
- Maven 3.6 ou supérieur
- PostgreSQL 12 ou supérieur
- IntelliJ IDEA (recommandé)

## Configuration de la base de données

1. Créer une base de données PostgreSQL nommée `gestionbancaires`
2. Modifier les paramètres de connexion dans `src/main/resources/META-INF/persistence.xml` si nécessaire :
   - URL : `jdbc:postgresql://localhost:5432/gestionbancaires`
   - Utilisateur : `postgres`
   - Mot de passe : `root`

## Installation

1. Cloner le dépôt :
   ```bash
   git clone https://github.com/votre-username/gestionbancaires.git
   cd gestionbancaires
   ```

2. Compiler le projet avec Maven :
   ```bash
   mvn clean install
   ```

3. Lancer l'application :
   ```bash
   mvn javafx:run
   ```

## Structure du projet

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── groupeisi/
│   │           └── gestionbancaires/
│   │               ├── entities/     # Classes d'entités JPA
│   │               ├── views/        # Contrôleurs des vues
│   │               ├── GestionCb.java
│   │               └── JpaUtils.java
│   └── resources/
│       ├── com/
│       │   └── groupeisi/
│       │       └── gestionbancaires/
│       │           └── views/        # Fichiers FXML
│       ├── styles/                   # Fichiers CSS
│       └── META-INF/
│           └── persistence.xml       # Configuration JPA
```

## Fonctionnalités

- Gestion des clients (CRUD)
- Gestion des cartes bancaires
- Suivi des transactions
- Programme de fidélité
- Interface utilisateur moderne et responsive

## Technologies utilisées

- JavaFX pour l'interface utilisateur
- JPA/Hibernate pour la persistance
- PostgreSQL comme base de données
- Maven pour la gestion des dépendances
- Lombok pour réduire le boilerplate

## Contribution

1. Fork le projet
2. Créer une branche pour votre fonctionnalité (`git checkout -b feature/AmazingFeature`)
3. Commit vos changements (`git commit -m 'Add some AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails. 