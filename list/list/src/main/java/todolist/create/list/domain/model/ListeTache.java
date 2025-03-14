package todolist.create.list.domain.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ListeTache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private EtatEnum etat;
    private Long idUser;
    private Long idProjet;
    private Long idTache;

    public ListeTache(){}

    public ListeTache(String description, EtatEnum etat, Long idUser, Long idProjet, Long idTache){
        this.description = description;
        this.etat = etat;
        this.idUser = idUser;
        this.idProjet = idProjet;
        this.idTache = idTache;
    }

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

    public Long getIdProjet(){
        return idProjet;
    }

    public void setIdProjet(Long idProfil){
        this.idProjet = idProfil;
    }

    public Long getIdTache(){
        return idTache;
    }

    public void setIdTache(Long idTache){
        this.idTache = idTache;
    }

}
