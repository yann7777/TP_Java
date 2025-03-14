package todolist.create.list.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import todolist.create.list.application.ports.input.ProjetUseCase;
import todolist.create.list.domain.model.Projet;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.ProjetEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.mapper.ProjetMapper;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.ProjetRepository;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.UserRepository;

@Service
public class ProjetService implements ProjetUseCase{

    private final ProjetRepository projetRepository;
    private final ProjetMapper projetMapper;
    private final UserRepository userRepository;

    public ProjetService(ProjetRepository projetRepository, ProjetMapper projetMapper, UserRepository userRepository) {
        this.projetRepository = projetRepository;
        this.projetMapper = projetMapper;
        this.userRepository = userRepository; 
    }

    @Override
    public Projet createProjet(String nom, Long idUser) {
        Projet projet = new Projet(nom, idUser);
        ProjetEntity projetEntity = projetMapper.toEntity(projet); 
        projetEntity = projetRepository.save(projetEntity);
        return projetMapper.toDomain(projetEntity); 
    }

    @Override
    public Optional<Projet> getProjet(Long id) {
        Optional<ProjetEntity> projetEntity = projetRepository.findById(id);
        return projetEntity.map(projetMapper::toDomain); 
    }

    @Override
    public List<Projet> getAllProjets() {
        List<ProjetEntity> projetEntities = projetRepository.findAll();
        return projetEntities.stream().map(projetMapper::toDomain).toList(); 
    }

    @Override
    public Projet updateProjet(Long id, String nom) {
        return projetRepository.findById(id).map(projetEntity -> {
            projetEntity.setNom(nom);
            projetEntity = projetRepository.save(projetEntity);
            return projetMapper.toDomain(projetEntity); 
        }).orElseThrow(() -> new RuntimeException("Projet non trouvé avec l'ID : " + id));
    }

    @Override
    public void deleteProjet(Long id) {
        projetRepository.deleteById(id);
    }


    @Override
    public Projet saveProjet(Projet projet) {
        ProjetEntity projetEntity = projetMapper.toEntity(projet);
        projetEntity = projetRepository.save(projetEntity);
        return projetMapper.toDomain(projetEntity);
    }
}
