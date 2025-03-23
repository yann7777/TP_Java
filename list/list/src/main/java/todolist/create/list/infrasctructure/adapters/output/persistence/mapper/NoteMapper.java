package todolist.create.list.infrasctructure.adapters.output.persistence.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import todolist.create.list.domain.model.Note;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.NoteEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.UserEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.UserRepository;

@Component
public class NoteMapper {

    @Autowired
    private UserRepository userRepository;

    public Note toDomain(NoteEntity noteEntity) {
        Note note = new Note(
            noteEntity.getContent(),
            noteEntity.getCreatedAt(),
            noteEntity.getUser().getId()
        );
        note.setId(noteEntity.getId());
        return note;
    }

    public NoteEntity toEntity(Note note) {
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setId(note.getId());
        noteEntity.setContent(note.getContent());
        noteEntity.setCreatedAt(note.getCreatedAt());
        
        // Récupérer l'utilisateur par son ID
        UserEntity user = userRepository.findById(note.getUserId())
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + note.getUserId()));
        noteEntity.setUser(user);
        
        return noteEntity;
    }
}