package org.acme.infrastructure.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.domain.models.Todo;
import org.acme.domain.repository.TodoRepository;
import org.acme.infrastructure.entities.TodoEntity;
import org.acme.infrastructure.mapper.TodoMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class TodoRepositoryImpl implements TodoRepository, PanacheRepositoryBase<TodoEntity, UUID> {

    @Override
    @Transactional
    public Todo save(Todo todo) {
        TodoEntity entity = TodoMapper.toEntity(todo);
        persist(entity);
        return TodoMapper.toDomain(entity);
    }

    @Override
    public List<Todo> findByUserId(UUID userId) {
        return find("userId", userId)
                .stream()
                .map(TodoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Todo> findTodoById(UUID id) {
        return findByIdOptional(id).map(TodoMapper::toDomain);
    }

    @Override
    @Transactional
    public boolean deleteById(UUID id) {
        return delete("id", id) > 0;
    }

    @Override
    @Transactional
    public boolean updateCompleted(UUID id, boolean completed) {
        return findByIdOptional(id).map(entity -> {
            entity.setCompleted(completed);
            entity.setCompletedAt(completed ? LocalDateTime.now() : null);
            return true;
        }).orElse(false);
    }

    @Override
    @Transactional
    public Optional<Todo> update(UUID id, Todo updated) {
        return findByIdOptional(id).map(entity -> {
            entity.setTitle(updated.getTitle());
            entity.setDescription(updated.getDescription());
            entity.setPriority(updated.getPriority());
            entity.setDueDate(updated.getDueDate());
            entity.setListId(updated.getListId());
            entity.setTags(updated.getTags());
            return TodoMapper.toDomain(entity);
        });
    }
}
