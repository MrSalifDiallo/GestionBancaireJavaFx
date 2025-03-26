package com.groupeisi.gestionbancaires.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private double montant;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_transaction", nullable = false)
    private TypeTransaction typeTransaction;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_transaction", nullable = false)
    private StatutTransaction statutTransaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carte_bancaire_id", nullable = false)
    private CarteBancaire carteBancaire;

    @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL)
    private AuthentificationTransaction authentification;

    public enum TypeTransaction {
        DEPOT,
        RETRAIT,
        PAIEMENT,
        VIREMENT
    }

    public enum StatutTransaction {
        EN_ATTENTE,
        VALIDEE,
        REJETEE,
        ANNULEE
    }
}
