package com.groupeisi.gestionbancaires.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "fidelite")
public class Fidelite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "points_acquis", nullable = false)
    private int pointsAcquis = 0;

    @Column(name = "niveau", nullable = false)
    @Enumerated(EnumType.STRING)
    private NiveauFidelite niveau = NiveauFidelite.BRONZE;

    @Column(name = "date_inscription", nullable = false)
    private LocalDate dateInscription;

    @Column(name = "derniere_mise_a_jour")
    private LocalDate derniereMiseAJour;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToOne(mappedBy = "fidelite", cascade = CascadeType.ALL)
    private CarteBancaire carteBancaire;

    public enum NiveauFidelite {
        BRONZE,
        ARGENT,
        OR,
        PLATINE
    }

    @PrePersist
    protected void onCreate() {
        dateInscription = LocalDate.now();
        derniereMiseAJour = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        derniereMiseAJour = LocalDate.now();
    }

    public void ajouterPoints(int points) {
        this.pointsAcquis += points;
        mettreAJourNiveau();
    }

    private void mettreAJourNiveau() {
        if (pointsAcquis >= 1000) {
            niveau = NiveauFidelite.OR;
        } else if (pointsAcquis >= 500) {
            niveau = NiveauFidelite.ARGENT;
        } else if (pointsAcquis >= 100) {
            niveau = NiveauFidelite.BRONZE;
        }
    }
}
