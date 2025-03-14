package todolist.create.list.domain.model;

public class Projet {
    private Long id;
    private String nom;
    private Long idUser; // Identifiant de l'utilisateur

    // Constructeurs
    public Projet() {}

    public Projet(String nom, Long idUser) {
        this.nom = nom;
        this.idUser = idUser;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}
