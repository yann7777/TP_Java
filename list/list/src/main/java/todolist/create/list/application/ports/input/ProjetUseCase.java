package todolist.create.list.application.ports.input;

import java.util.List;
import java.util.Optional;

import todolist.create.list.domain.model.Projet;

public interface ProjetUseCase{
    Projet createProjet(String nom, Long idUser);
    Optional<Projet> getProjet(Long id);
    List<Projet> getAllProjets();
    Projet saveProjet(Projet projet);
    Projet updateProjet(Long id, String nom, Long idUser);
    void deleteProjet(Long id);
    List<Projet> getProjetsByUserId(Long userId); // Récupérer les projets par ID utilisateur

}