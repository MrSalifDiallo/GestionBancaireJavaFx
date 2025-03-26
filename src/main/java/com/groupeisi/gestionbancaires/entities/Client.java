package com.groupeisi.gestionbancaires.entities;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String adresse;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @Column(nullable = false)
    private String statut = "ACTIF";

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<CarteBancaire> cartesBancaires;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Fidelite fidelite;

    // Méthode appelée avant la persistance
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}
