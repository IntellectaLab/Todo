package org.acme.application.usecase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.application.dto.CreateTaskListDto;
import org.acme.domain.models.TaskList;
import org.acme.domain.repository.TaskListRepository;
import org.acme.infrastructure.security.AuthContext;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class CreateTaskListUseCase {

    private final TaskListRepository taskListRepository;
    private final AuthContext authContext;

    @Inject
    public CreateTaskListUseCase(TaskListRepository taskListRepository, AuthContext authContext) {
        this.taskListRepository = taskListRepository;
        this.authContext = authContext;
    }

    public TaskList execute(CreateTaskListDto dto) {
        TaskList list = new TaskList();
        list.setId(UUID.randomUUID());
        list.setTitle(dto.getTitle());
        list.setColor(dto.getColor() != null ? dto.getColor() : "#6366F1");
        list.setIcon(dto.getIcon() != null ? dto.getIcon() : "📋");
        list.setUserId(authContext.getUser().getId());
        list.setCreatedAt(LocalDateTime.now());
        return taskListRepository.save(list);
    }
}
