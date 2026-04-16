package org.acme.domain.repository;

import org.acme.domain.models.Todo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoRepository {
    Todo save(Todo todo);
    List<Todo> findByUserId(UUID userId);
    Optional<Todo> findTodoById(UUID id);
    boolean deleteById(UUID id);
    boolean updateCompleted(UUID id, boolean completed);
    Optional<Todo> update(UUID id, Todo updated);
}
