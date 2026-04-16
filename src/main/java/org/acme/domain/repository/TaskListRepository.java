package org.acme.domain.repository;

import org.acme.domain.models.TaskList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskListRepository {
    TaskList save(TaskList taskList);
    List<TaskList> findByUserId(UUID userId);
    Optional<TaskList> findListById(UUID id);
    boolean deleteById(UUID id);
    Optional<TaskList> update(UUID id, TaskList updated);
}
