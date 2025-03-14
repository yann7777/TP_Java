package todolist.create.list.infrasctructure.adapters;

import java.util.List;
import java.util.Optional;

import todolist.create.list.application.ports.output.ProjetRepositoryPort;
import todolist.create.list.domain.model.Projet;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.ProjetEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.mapper.ProjetMapper;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.ProjetRepository;

public class ProjetRepositoryAdapter implements ProjetRepositoryPort {

    private final ProjetRepository projetRepository;

    public ProjetRepositoryAdapter(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }

    @Override
    public Projet save(Projet projet) {
        // Convertir le modèle de domaine en entité
        ProjetEntity projetEntity = new ProjetEntity();
        projetEntity = projetRepository.save(projetEntity); // Sauvegarder l'entité
        return ProjetMapper.toModel(projetEntity); // Convertir l'entité sauvegardée en modèle de domaine
    }

    @Override
    public Optional<Projet> findById(Long id) {
        // Récupérer l'entité via l'id et la convertir en modèle de domaine
        Optional<ProjetEntity> projetEntity = projetRepository.findById(id);
        return projetEntity.map(ProjetMapper::toModel); // Mapper l'entité en modèle
    }

    @Override
    public List<Projet> findAll() {
        // Récupérer toutes les entités et les convertir en modèles de domaine
        List<ProjetEntity> projetEntities = projetRepository.findAll();
        return projetEntities.stream()
                             .map(ProjetMapper::toModel) // Mapper chaque entité en modèle
                             .toList();
    }

    @Override
    public void deleteById(Long id) {
        // Supprimer le projet par son id
        projetRepository.deleteById(id);
    }
}
