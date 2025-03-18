package todolist.create.list.infrasctructure.adapters.output.persistence.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import todolist.create.list.domain.model.EtatEnum;

@Entity
@Table(name = "historique_tache")
public class HistoriqueTacheEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "tache_id", nullable = false)
    private TacheEntity tache;
    
    @Enumerated(EnumType.STRING)
    private EtatEnum ancienEtat;
    
    @Enumerated(EnumType.STRING)
    private EtatEnum nouvelEtat;
    
    private LocalDateTime dateModification;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    
    private String commentaire;
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public TacheEntity getTache() {
        return tache;
    }
    
    public void setTache(TacheEntity tache) {
        this.tache = tache;
    }
    
    public EtatEnum getAncienEtat() {
        return ancienEtat;
    }
    
    public void setAncienEtat(EtatEnum ancienEtat) {
        this.ancienEtat = ancienEtat;
    }
    
    public EtatEnum getNouvelEtat() {
        return nouvelEtat;
    }
    
    public void setNouvelEtat(EtatEnum nouvelEtat) {
        this.nouvelEtat = nouvelEtat;
    }
    
    public LocalDateTime getDateModification() {
        return dateModification;
    }
    
    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }
    
    public UserEntity getUser() {
        return user;
    }
    
    public void setUser(UserEntity user) {
        this.user = user;
    }
    
    public String getCommentaire() {
        return commentaire;
    }
    
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}