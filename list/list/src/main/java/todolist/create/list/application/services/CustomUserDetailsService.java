package todolist.create.list.application.services;

import org.springframework.stereotype.Service;

import todolist.create.list.domain.model.User;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.UserEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.mapper.UserMapper;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.UserRepository;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService { // Implémentez UserDetailsService

    private final UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

@Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + email));

    String role = userEntity.getRole();
    log.info("Rôle de l'utilisateur {} : {}", email, role);

    if (role == null || role.isEmpty()) {
        throw new InternalAuthenticationServiceException("Aucun rôle défini pour l'utilisateur : " + email);
    }

    return new org.springframework.security.core.userdetails.User(
        userEntity.getEmail(),
        userEntity.getPassword(),
        Collections.singletonList(new SimpleGrantedAuthority(role))
    );
}
    

    public void registerUser(User user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        userRepository.save(userEntity);
    }

    public User findUserByEmail(String email) {
        // Récupérer l'entité UserEntity à partir de l'email
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return UserMapper.toDomain(userEntity);
    }
}

