package todolist.create.list.application.ports.input;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import todolist.create.list.domain.model.EtatEnum;
import todolist.create.list.domain.model.Tache;

public interface TacheUseCase {
    Tache createTache(String titre, String description, EtatEnum etat, Long idUser, Long idProjet, LocalDateTime dateRappel, boolean pinned, boolean archived);
    Optional<Tache> getTache(Long id);
    List<Tache> getAllTaches();
    Tache saveTache(Tache tache);
    Tache updateTache(Long id, String titre, String description, EtatEnum etat, LocalDateTime dateRappel, Long userId);
    List<Tache> getTachesByUserId(Long idUser);
    void deleteTache(Long id, Long userId);
    Tache pinTache(Long id, Long userId);
    Tache unpinTache(Long id, Long userId);
    List<Tache> rechercherTaches(Long idUser, String terme);
    Tache assignTache(Long tacheId, Long assigneeId, Long currentUserId);
    Tache unassignTache(Long tacheId, Long currentUserId);
    List<Tache> getAssignedTaches(Long userId);
    Tache archivedTache(Long id, Long userId);
    Tache unarchivedTache(Long id, Long userId);
    List<Tache> getArchivedTaches(Long userId);
}