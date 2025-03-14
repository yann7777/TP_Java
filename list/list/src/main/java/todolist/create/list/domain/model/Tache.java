package todolist.create.list.domain.model;

public class Tache {
    private Long id;
    private String titre;
    private String description;
    private EtatEnum etat;
    private Long idUser;
    private Long idProjet;

    public Tache(){}

    public Tache(String titre, String description, EtatEnum etat, Long idUser, Long idProjet){
        this.titre = titre;
        this.description = description;
        this.etat = etat;
        this.idUser = idUser;
        this.idProjet = idProjet;
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

}
