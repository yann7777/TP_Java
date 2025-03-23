package todolist.create.list.application.services;

import org.springframework.stereotype.Service;
import todolist.create.list.application.ports.input.NoteUseCase;
import todolist.create.list.domain.model.Note;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.NoteEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.entity.UserEntity;
import todolist.create.list.infrasctructure.adapters.output.persistence.mapper.NoteMapper;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.NoteRepository;
import todolist.create.list.infrasctructure.adapters.output.persistence.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService implements NoteUseCase {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final NoteMapper noteMapper;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.noteMapper = noteMapper;
    }

    @Override
    public Note createNote(String content, Long userId) {
        // Vérifier que l'utilisateur existe
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + userId));
        
        // Créer la note
        Note note = new Note(content, new Date(), userId);
        NoteEntity noteEntity = noteMapper.toEntity(note);
        noteEntity.setUser(user);
        
        // Enregistrer la note
        noteEntity = noteRepository.save(noteEntity);
        return noteMapper.toDomain(noteEntity);
    }

    @Override
    public Optional<Note> getNote(Long id) {
        Optional<NoteEntity> noteEntity = noteRepository.findById(id);
        return noteEntity.map(noteMapper::toDomain);
    }

    @Override
    public List<Note> getNotesByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + userId));
        List<NoteEntity> noteEntities = noteRepository.findByUserId(userId);
        return noteEntities.stream().map(noteMapper::toDomain).toList();
    }

    @Override
    public void deleteNote(Long id, Long userId) {
        NoteEntity noteEntity = noteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Note non trouvée avec l'ID : " + id));
        
        if (!noteEntity.getUser().getId().equals(userId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer cette note.");
        }
        
        noteRepository.deleteById(id);
    }

    @Override
    public Note updateNote(Long id, String content, Long userId) {
        NoteEntity noteEntity = noteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Note non trouvée avec l'ID : " + id));
        
        if (!noteEntity.getUser().getId().equals(userId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cette note.");
        }
        
        noteEntity.setContent(content);
        noteEntity = noteRepository.save(noteEntity);
        return noteMapper.toDomain(noteEntity);
    }

    @Override
    public Note saveNote(Note note) {
        NoteEntity noteEntity = noteMapper.toEntity(note);
        noteEntity = noteRepository.save(noteEntity);
        return noteMapper.toDomain(noteEntity);
    }

    @Override
    public List<Note> getAllNotes() {
        List<NoteEntity> noteEntities = noteRepository.findAll();
        return noteEntities.stream().map(noteMapper::toDomain).toList();
    }
    

}