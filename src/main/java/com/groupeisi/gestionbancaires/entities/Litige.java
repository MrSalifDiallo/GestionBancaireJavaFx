package com.groupeisi.gestionbancaires.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "litige")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Litige {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @Column(name = "motif", nullable = false)
    private String motif;

    @Column(name = "date_signalement", nullable = false)
    private Date dateSignalement;

    @Column(name = "statut", nullable = false)
    private String statut = "EN_COURS"; // EN_COURS, RESOLU, REJETE

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @PrePersist
    protected void onCreate() {
        dateSignalement = new Date();
    }
}
