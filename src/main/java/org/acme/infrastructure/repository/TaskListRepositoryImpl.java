package org.acme.infrastructure.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.domain.models.TaskList;
import org.acme.domain.repository.TaskListRepository;
import org.acme.infrastructure.entities.TaskListEntity;
import org.acme.infrastructure.mapper.TaskListMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class TaskListRepositoryImpl implements TaskListRepository, PanacheRepositoryBase<TaskListEntity, UUID> {

    @Override
    @Transactional
    public TaskList save(TaskList taskList) {
        TaskListEntity entity = TaskListMapper.toEntity(taskList);
        persist(entity);
        return TaskListMapper.toDomain(entity);
    }

    @Override
    public List<TaskList> findByUserId(UUID userId) {
        return find("userId", userId)
                .stream()
                .map(TaskListMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TaskList> findListById(UUID id) {
        return findByIdOptional(id).map(TaskListMapper::toDomain);
    }

    @Override
    @Transactional
    public boolean deleteById(UUID id) {
        return delete("id", id) > 0;
    }

    @Override
    @Transactional
    public Optional<TaskList> update(UUID id, TaskList updated) {
        return findByIdOptional(id).map(entity -> {
            entity.setTitle(updated.getTitle());
            entity.setColor(updated.getColor());
            entity.setIcon(updated.getIcon());
            return TaskListMapper.toDomain(entity);
        });
    }
}
