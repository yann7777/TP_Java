package todolist.create.list.infrasctructure.adapters.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import todolist.create.list.infrasctructure.adapters.output.persistence.entity.ProjetEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.UserEntity;

public interface ProjetRepository extends JpaRepository<ProjetEntity, Long> {
     List<ProjetEntity> findByUser(UserEntity user); 
     public List<ProjetEntity> findByUserId(Long idUser);
}
