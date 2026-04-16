package org.acme.application.usecase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.application.dto.CreateTodoDto;
import org.acme.domain.models.Todo;
import org.acme.domain.repository.TodoRepository;
import org.acme.infrastructure.security.AuthContext;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class CreateTodoUseCase {

    private final TodoRepository todoRepository;
    private final AuthContext authContext;

    @Inject
    public CreateTodoUseCase(TodoRepository todoRepository, AuthContext authContext) {
        this.todoRepository = todoRepository;
        this.authContext = authContext;
    }

    public Todo execute(CreateTodoDto dto) {
        Todo todo = new Todo();
        todo.setId(UUID.randomUUID());
        todo.setTitle(dto.getTitle());
        todo.setDescription(dto.getDescription());
        todo.setCompleted(false);
        todo.setCreatedAt(LocalDateTime.now());
        todo.setUserId(authContext.getUser().getId());
        todo.setPriority(dto.getPriority() != null ? dto.getPriority() : "MEDIUM");
        todo.setDueDate(dto.getDueDate());
        todo.setListId(dto.getListId());
        todo.setTags(dto.getTags());
        return todoRepository.save(todo);
    }
}
