package todolist.create.list.infrasctructure.adapters.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import todolist.create.list.infrasctructure.adapters.output.persistence.entity.TacheEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.UserEntity;

public interface TacheRepository extends JpaRepository<TacheEntity, Long> {
    List<TacheEntity> findByUser(UserEntity user);
    List<TacheEntity> findByUserAndTitreContainingIgnoreCase(UserEntity user, String terme);
    List<TacheEntity> findByUserAndDescriptionContainingIgnoreCase(UserEntity user, String terme);
    List<TacheEntity> findByUserAndTitreContainingIgnoreCaseOrUserAndDescriptionContainingIgnoreCase(UserEntity user, String TitreTerm, UserEntity userSame, String descriptionTerm);
    List<TacheEntity> findByAssignee(UserEntity assignee);
    List<TacheEntity> findByUserAndArchived(UserEntity user, boolean archived);
}
