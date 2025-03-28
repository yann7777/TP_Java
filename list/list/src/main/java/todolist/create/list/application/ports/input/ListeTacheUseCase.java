package todolist.create.list.application.ports.input;

import java.util.List;
import java.util.Optional;

import todolist.create.list.domain.model.EtatEnum;
import todolist.create.list.domain.model.ListeTache;

public interface ListeTacheUseCase {
    ListeTache createListeTache(String description, EtatEnum etat, Long idProjet, Long idTache, Long idUser);
    Optional<ListeTache> getListeTache(Long id);
    List<ListeTache> getAllListeTaches();
    ListeTache saveListeTache(ListeTache tache);
    ListeTache updateListeTache(Long id, String description, EtatEnum etat);
    void deleteListeTache(Long id);
    List<ListeTache> getListTachesByUserId(Long idUser);
    List<ListeTache> getListTachesByTacheId(Long tacheId);
    ListeTache moveListeToTache(Long listeId, Long newTacheId, Long userId);

    ListeTache completedListeTache(Long id, Long userId);
    ListeTache uncompletedListeTache(Long id, Long userId);
    List<ListeTache> getCompletedListeTache(Long id);
}
