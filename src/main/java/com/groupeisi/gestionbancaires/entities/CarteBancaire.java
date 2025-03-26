package com.groupeisi.gestionbancaires.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cartes_bancaires")
public class CarteBancaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @Column(name = "date_expiration", nullable = false)
    private LocalDate dateExpiration;

    @Column(name = "cvv", nullable = false)
    private String cvv;

    @Column(nullable = false)
    private double solde;

    @Column(nullable = false)
    private String statut = "ACTIVE";

    @Column(name = "limite_journaliere")
    private double limiteJournaliere;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "carteBancaire", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    @OneToOne(mappedBy = "carteBancaire", cascade = CascadeType.ALL)
    private Fidelite fidelite;
}
