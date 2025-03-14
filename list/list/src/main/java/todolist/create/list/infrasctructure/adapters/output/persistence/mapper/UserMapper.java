package todolist.create.list.infrasctructure.adapters.output.persistence.mapper;

import org.springframework.stereotype.Component;

import todolist.create.list.domain.model.User;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.UserEntity;

@Component
public class UserMapper {
    
    public static User toDomain(UserEntity userEntity){
        User user = new User(userEntity.getPrenom(), userEntity.getNom(), userEntity.getEmail(), userEntity.getPassword());
        user.setId(userEntity.getId());
        return user;
    }

    public static UserEntity toEntity(User user){
        UserEntity userEntity = new UserEntity();
        userEntity.setPrenom(user.getPrenom());
        userEntity.setNom(user.getNom());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        return userEntity;
    }
}
