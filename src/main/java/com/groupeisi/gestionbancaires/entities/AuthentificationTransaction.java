package com.groupeisi.gestionbancaires.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "authentification_transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthentificationTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @Column(name = "code_otp", nullable = false)
    private String codeOtp;

    @Column(name = "statut", nullable = false)
    private String statut = "EN_ATTENTE"; // EN_ATTENTE, VALIDE, EXPIRE

    @Column(name = "date_generation", nullable = false)
    private Date dateGeneration;

    @PrePersist
    protected void onCreate() {
        dateGeneration = new Date();
    }
}
