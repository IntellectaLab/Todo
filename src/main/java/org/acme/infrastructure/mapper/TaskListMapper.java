package org.acme.infrastructure.mapper;

import org.acme.domain.models.TaskList;
import org.acme.infrastructure.entities.TaskListEntity;

public class TaskListMapper {

    public static TaskList toDomain(TaskListEntity entity) {
        TaskList list = new TaskList();
        list.setId(entity.getId());
        list.setTitle(entity.getTitle());
        list.setColor(entity.getColor());
        list.setIcon(entity.getIcon());
        list.setUserId(entity.getUserId());
        list.setCreatedAt(entity.getCreatedAt());
        return list;
    }

    public static TaskListEntity toEntity(TaskList list) {
        TaskListEntity entity = new TaskListEntity();
        entity.setId(list.getId());
        entity.setTitle(list.getTitle());
        entity.setColor(list.getColor());
        entity.setIcon(list.getIcon());
        entity.setUserId(list.getUserId());
        entity.setCreatedAt(list.getCreatedAt());
        return entity;
    }
}
