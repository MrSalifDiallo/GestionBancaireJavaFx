package com.groupeisi.gestionbancaires;

import com.groupeisi.gestionbancaires.entities.*;
import com.groupeisi.gestionbancaires.entities.Transaction.TypeTransaction;
import com.groupeisi.gestionbancaires.entities.Transaction.StatutTransaction;
import com.groupeisi.gestionbancaires.services.JpaUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DataInitializer {

    public static void initializeData(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // Création des clients
            Client client1 = new Client();
            client1.setNom("Dupont");
            client1.setPrenom("Jean");
            client1.setEmail("jean.dupont@email.com");
            client1.setTelephone("0123456789");
            client1.setAdresse("123 rue de Paris");
            client1.setDateCreation(LocalDateTime.now());
            client1.setStatut("ACTIF");
            em.persist(client1);

            Client client2 = new Client();
            client2.setNom("Martin");
            client2.setPrenom("Marie");
            client2.setEmail("marie.martin@email.com");
            client2.setTelephone("9876543210");
            client2.setAdresse("456 avenue des Champs-Élysées");
            client2.setDateCreation(LocalDateTime.now());
            client2.setStatut("ACTIF");
            em.persist(client2);

            // Création des cartes bancaires
            CarteBancaire carte1 = new CarteBancaire();
            carte1.setNumero("1234567890123456");
            carte1.setDateExpiration(LocalDate.now().plusYears(3));
            carte1.setCvv("123");
            carte1.setSolde(1000.0);
            carte1.setStatut("ACTIVE");
            carte1.setLimiteJournaliere(1000.0);
            carte1.setClient(client1);
            em.persist(carte1);

            CarteBancaire carte2 = new CarteBancaire();
            carte2.setNumero("9876543210987654");
            carte2.setDateExpiration(LocalDate.now().plusYears(3));
            carte2.setCvv("456");
            carte2.setSolde(2000.0);
            carte2.setStatut("ACTIVE");
            carte2.setLimiteJournaliere(2000.0);
            carte2.setClient(client2);
            em.persist(carte2);

            // Création des transactions
            Transaction trans1 = new Transaction();
            trans1.setDate(LocalDate.now());
            trans1.setMontant(100.0);
            trans1.setTypeTransaction(TypeTransaction.RETRAIT);
            trans1.setStatutTransaction(StatutTransaction.VALIDEE);
            trans1.setCarteBancaire(carte1);
            em.persist(trans1);

            Transaction trans2 = new Transaction();
            trans2.setDate(LocalDate.now());
            trans2.setMontant(200.0);
            trans2.setTypeTransaction(TypeTransaction.DEPOT);
            trans2.setStatutTransaction(StatutTransaction.VALIDEE);
            trans2.setCarteBancaire(carte2);
            em.persist(trans2);

            // Création des comptes fidélité
            Fidelite compte1 = new Fidelite();
            compte1.setPointsAcquis(100);
            compte1.setClient(client1);
            em.persist(compte1);

            Fidelite compte2 = new Fidelite();
            compte2.setPointsAcquis(200);
            compte2.setClient(client2);
            em.persist(compte2);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    public static void main(String[] args) {
        EntityManager em = null;
        try {
            em = JpaUtils.getEm();
            initializeData(em);
            System.out.println("Données initiales créées avec succès");
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Erreur lors de l'initialisation des données : " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
} 