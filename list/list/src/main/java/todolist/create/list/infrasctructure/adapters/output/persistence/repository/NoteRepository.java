package todolist.create.list.infrasctructure.adapters.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.NoteEntity;

import java.util.List;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    List<NoteEntity> findByUserId(Long userId);
}