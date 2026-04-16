package org.acme.infrastructure.mapper;

import org.acme.domain.models.Subtask;
import org.acme.infrastructure.entities.SubtaskEntity;

public class SubtaskMapper {

    public static Subtask toDomain(SubtaskEntity entity) {
        Subtask subtask = new Subtask();
        subtask.setId(entity.getId());
        subtask.setTitle(entity.getTitle());
        subtask.setCompleted(entity.isCompleted());
        subtask.setTodoId(entity.getTodoId());
        subtask.setCreatedAt(entity.getCreatedAt());
        return subtask;
    }

    public static SubtaskEntity toEntity(Subtask subtask) {
        SubtaskEntity entity = new SubtaskEntity();
        entity.setId(subtask.getId());
        entity.setTitle(subtask.getTitle());
        entity.setCompleted(subtask.isCompleted());
        entity.setTodoId(subtask.getTodoId());
        entity.setCreatedAt(subtask.getCreatedAt());
        return entity;
    }
}
