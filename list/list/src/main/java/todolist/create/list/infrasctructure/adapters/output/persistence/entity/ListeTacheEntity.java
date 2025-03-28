package todolist.create.list.infrasctructure.adapters.output.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "listetache")
public class ListeTacheEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private boolean completed;

    @Enumerated(EnumType.STRING)
    private EtatEnum etat;

    @CreationTimestamp 
    @Column(name = "date", nullable = false, updatable = false) // Ne pas permettre la mise à jour manuelle
    private LocalDateTime date;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "projet_id", nullable = false)
    private ProjetEntity projet;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "tache_id", nullable = false)
    private TacheEntity tache;

    
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public ProjetEntity getProjet(){
        return projet;
    }

    public void setProjet(ProjetEntity projet){
        this.projet = projet;
    }

    public TacheEntity getTache(){
        return tache;
    }

    public void setTache(TacheEntity tache){
        this.tache = tache;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public EtatEnum getEtat(){
        return etat;
    }

    public void setEtat(EtatEnum etat){
        this.etat = etat;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isCompleted(){
        return completed;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }
}