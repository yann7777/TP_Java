package todolist.create.list.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import todolist.create.list.application.ports.input.UserUseCase;
import todolist.create.list.domain.model.User;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.UserEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.mapper.UserMapper;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.UserRepository;

@Service
public class UserService implements UserUseCase {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String prenom, String nom, String email, String password) {
        User user = new User(prenom, nom, email, password);
        UserEntity userEntity = UserMapper.toEntity(user);
        userEntity = userRepository.save(userEntity);
        return UserMapper.toDomain(userEntity);
    }
    
    @Override
    public Optional<User> getUser(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity.map(UserMapper::toDomain);
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream().map(UserMapper::toDomain).toList();
    }

    @Override
    public User saveUser(User user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        userEntity = userRepository.save(userEntity);
        return UserMapper.toDomain(userEntity);
    }

    @Override
    public User updateUser(Long id, String prenom, String nom, String email, String password) {
        return userRepository.findById(id).map(userEntity -> {
            userEntity.setPrenom(prenom);
            userEntity.setNom(nom);
            userEntity.setEmail(email);
            userEntity.setPassword(password);
            userEntity = userRepository.save(userEntity);
            return UserMapper.toDomain(userEntity);
        }).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + id));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void registerUser(User user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        userEntity = userRepository.save(userEntity);  // Sauvegarde de l'entité et récupération de l'ID généré
        user.setId(userEntity.getId());  // Assurez-vous que l'ID est bien défini dans l'objet User
    }    
}
