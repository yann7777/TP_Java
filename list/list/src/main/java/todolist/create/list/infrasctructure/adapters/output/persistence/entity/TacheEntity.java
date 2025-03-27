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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import todolist.create.list.domain.model.EtatEnum;
import java.util.List;

@Entity
@Table(name = "tache")
public class TacheEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    @Enumerated(EnumType.STRING)
    private EtatEnum etat;
    private LocalDateTime dateRappel;
    private boolean pinned;
    private boolean archived;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "projet_id", nullable = false)
    private ProjetEntity projet;

    @OneToMany(mappedBy = "tache", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListeTacheEntity> listeTaches;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private UserEntity assignee;

    // Getters et setters
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getTitre(){
        return titre;
    }

    public void setTitre(String titre){
        this.titre = titre;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public EtatEnum getEtat(){
        return etat;
    }

    public void setEtat(EtatEnum etat){
        this.etat = etat;
    }

    public UserEntity getUser(){
        return user;
    }

    public void setUser(UserEntity user){
        this.user = user;
    }

    public ProjetEntity getProjet(){
        return projet;
    }

    public void setProjet(ProjetEntity projet){
        this.projet = projet;
    }

    public LocalDateTime getDateRappel(){
        return dateRappel;
    }

    public void setDateRappel(LocalDateTime dateRappel){
        this.dateRappel = dateRappel;
    }

    public List<ListeTacheEntity> getListeTaches() {
        return listeTaches;
    }

    public void setListeTaches(List<ListeTacheEntity> listeTaches) {
        this.listeTaches = listeTaches;
    }

    public boolean isPinned(){
        return pinned;
    }

    public void setPinned(boolean pinned){
        this.pinned = pinned;
    }

    public UserEntity getAssignee(){
        return assignee;
    }

    public void setAssignee(UserEntity assignee){
        this.assignee = assignee;
    }

    public boolean isArchived(){
        return archived;
    }

    public void setArchived(boolean archived){
        this.archived = archived;
    }
}