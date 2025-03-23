package todolist.create.list.application.ports.input;

import todolist.create.list.domain.model.Note;

import java.util.List;
import java.util.Optional;

public interface NoteUseCase {
    Note createNote(String content, Long userId);
    Optional<Note> getNote(Long id);
    List<Note> getNotesByUserId(Long userId);
    void deleteNote(Long id, Long userId);
    Note updateNote(Long id, String content, Long userId);
    Note saveNote(Note note);
    List<Note> getAllNotes();
}