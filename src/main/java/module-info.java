module com.groupeisi.gestionbancaires {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires static lombok;
    requires java.naming;
    requires jakarta.persistence;

    opens com.groupeisi.gestionbancaires to javafx.fxml;
    opens com.groupeisi.gestionbancaires.entities to org.hibernate.orm.core;
    opens com.groupeisi.gestionbancaires.views to javafx.fxml;
    opens com.groupeisi.gestionbancaires.services to javafx.fxml;

    exports com.groupeisi.gestionbancaires;
    exports com.groupeisi.gestionbancaires.entities;
    exports com.groupeisi.gestionbancaires.views;
    exports com.groupeisi.gestionbancaires.services;
    exports com.groupeisi.gestionbancaires.utils;
}
