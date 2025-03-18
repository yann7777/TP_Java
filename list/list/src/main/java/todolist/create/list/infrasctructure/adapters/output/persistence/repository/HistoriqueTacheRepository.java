package todolist.create.list.infrasctructure.adapters.output.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.HistoriqueTacheEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.TacheEntity;

@Repository
public interface HistoriqueTacheRepository extends JpaRepository<HistoriqueTacheEntity, Long> {
    List<HistoriqueTacheEntity> findByTacheOrderByDateModificationDesc(TacheEntity tache);
}