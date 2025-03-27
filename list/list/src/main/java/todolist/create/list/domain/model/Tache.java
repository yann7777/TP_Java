package todolist.create.list.domain.model;

import java.time.LocalDateTime;

public class Tache {
    private Long id;
    private String titre;
    private String description;
    private EtatEnum etat;
    private Long idUser;
    private Long idAssignee; 
    private Long idProjet;
    private LocalDateTime dateRappel;
    private boolean pinned;
    private boolean archived;

    public Tache(){}

    public Tache(Long id, String titre, String description, EtatEnum etat, Long idUser, Long idAssignee, Long idProjet, LocalDateTime dateRappel, boolean pinned, boolean archived){
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.etat = etat;
        this.idUser = idUser;
        this.idAssignee = idAssignee;
        this.idProjet = idProjet;
        this.dateRappel = dateRappel;
        this.pinned = pinned;
        this.archived = archived;
    }

    public Tache(String titre, String description, EtatEnum etat, Long idUser, Long idAssignee, Long idProjet, LocalDateTime dateRappel, boolean pinned, boolean archived){
        this.titre = titre;
        this.description = description;
        this.etat = etat;
        this.idUser = idUser;
        this.idAssignee = idAssignee;
        this.idProjet = idProjet;
        this.dateRappel = dateRappel;
        this.pinned = pinned;
        this.archived = archived;
    }

    public Tache(String titre, String description, EtatEnum etat, Long idUser, Long idProjet, LocalDateTime dateRappel, boolean pinned, boolean archived) {
        this(titre, description, etat, idUser, null, idProjet, dateRappel, pinned, archived);
    }

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

    public Long getIdUser(){
        return idUser;        
    }

    public void setIdUser(Long idUser){
        this.idUser = idUser;
    }
    
    public Long getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(Long idProjet) {
        this.idProjet = idProjet;
    }

    public LocalDateTime getDateRappel() {
        return dateRappel;
    }

    public void setDateRappel(LocalDateTime dateRappel) {
        this.dateRappel = dateRappel;
    }

    public boolean isPinned(){
        return pinned;
    }

    public void setPinned(boolean pinned){
        this.pinned = pinned;
    }
    
    public Long getIdAssigned(){
        return idAssignee;
    }

    public void setIdAssigned(Long idAssignee){
        this.idAssignee = idAssignee;
    }

    public boolean isArchived(){
        return archived;
    }

    public void setArchived(boolean archived){
        this.archived = archived;
    }

}
