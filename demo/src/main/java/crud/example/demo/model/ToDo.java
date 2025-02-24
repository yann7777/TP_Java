package crud.example.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Document(collection = "todolist") // Correction : MongoDB utilise @Document au lieu de @Entity
public class ToDo {
    @org.springframework.data.annotation.Id
    private String id;  // MongoDB génère automatiquement un id de type String

    private String titre;
    private String description;
    private boolean status;

    // Exception personnalisée pour gérer les erreurs de ressource non trouvée
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    public ToDo() {}

    public ToDo(String titre, String description) {
        this.titre = titre;
        this.description = description;
        this.status = false;
    }

    // Getters et Setters
    public String getId() { return id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
}
