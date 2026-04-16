package org.acme.domain.repository;

import org.acme.domain.models.Subtask;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubtaskRepository {
    Subtask save(Subtask subtask);
    List<Subtask> findByTodoId(UUID todoId);
    Optional<Subtask> findSubtaskById(UUID id);
    boolean updateCompleted(UUID id, boolean completed);
    boolean deleteById(UUID id);
}
