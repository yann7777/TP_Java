package todolist.create.list.infrasctructure.adapters.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import todolist.create.list.infrasctructure.adapters.output.persistence.entity.ListeTacheEntity;

public interface ListeTacheRepository extends JpaRepository<ListeTacheEntity, Long>{
    List<ListeTacheEntity> findByUserId(Long idUser);
}
