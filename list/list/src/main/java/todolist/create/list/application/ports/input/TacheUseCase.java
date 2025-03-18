package todolist.create.list.application.ports.input;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import todolist.create.list.domain.model.EtatEnum;
import todolist.create.list.domain.model.Tache;

public interface TacheUseCase {
    Tache createTache(String titre, String description, EtatEnum etat, Long idUser, Long idProjet, LocalDateTime dateRappel, boolean pinned);
    Optional<Tache> getTache(Long id);
    List<Tache> getAllTaches();
    Tache saveTache(Tache tache);
    Tache updateTache(Long id, String titre, String description, EtatEnum etat, LocalDateTime dateRappel);
    List<Tache> getTachesByUserId(Long idUser);
    void deleteTache(Long id);
    Tache pinTache(Long id, Long userId);
    Tache unpinTache(Long id, Long userId);
}