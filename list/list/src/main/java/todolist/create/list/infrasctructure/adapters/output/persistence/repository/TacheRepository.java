package todolist.create.list.infrasctructure.adapters.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import todolist.create.list.infrasctructure.adapters.output.persistence.entity.TacheEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.UserEntity;

public interface TacheRepository extends JpaRepository<TacheEntity, Long> {
    List<TacheEntity> findByUser(UserEntity user);
}
