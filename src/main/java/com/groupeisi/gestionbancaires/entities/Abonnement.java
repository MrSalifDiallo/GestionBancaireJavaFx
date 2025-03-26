package com.groupeisi.gestionbancaires.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "abonnement")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Abonnement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "carte_id", nullable = false)
    private CarteBancaire carte;

    @Column(name = "service", nullable = false)
    private String service;

    @Column(name = "montant", nullable = false)
    private double montant;

    @Column(name = "date_prelevement", nullable = false)
    private Date datePrelevement;

    @Column(name = "statut", nullable = false)
    private String statut = "ACTIF"; // ACTIF, ANNULE

    @Column(name = "date_creation", nullable = false)
    private Date dateCreation;

    @PrePersist
    protected void onCreate() {
        dateCreation = new Date();
    }
}
