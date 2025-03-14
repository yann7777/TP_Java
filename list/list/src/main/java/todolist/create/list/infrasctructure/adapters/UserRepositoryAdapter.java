package todolist.create.list.infrasctructure.adapters;

import java.util.List;
import java.util.Optional;

import todolist.create.list.application.ports.output.UserRepositoryPort;
import todolist.create.list.domain.model.User;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.UserEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.mapper.UserMapper;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.UserRepository;

public class UserRepositoryAdapter implements UserRepositoryPort {
        private final UserRepository userRepository;

    public UserRepositoryAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        userEntity = userRepository.save(userEntity);
        return UserMapper.toDomain(userEntity);
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity.map(UserMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream().map(UserMapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
