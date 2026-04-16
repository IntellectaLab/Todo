package org.acme.infrastructure.mapper;

import org.acme.domain.models.Todo;
import org.acme.infrastructure.entities.TodoEntity;

public class TodoMapper {

    public static Todo toDomain(TodoEntity entity) {
        Todo todo = new Todo();
        todo.setId(entity.getId());
        todo.setTitle(entity.getTitle());
        todo.setDescription(entity.getDescription());
        todo.setCompleted(entity.isCompleted());
        todo.setCreatedAt(entity.getCreatedAt());
        todo.setUserId(entity.getUserId());
        todo.setPriority(entity.getPriority());
        todo.setDueDate(entity.getDueDate());
        todo.setListId(entity.getListId());
        todo.setTags(entity.getTags());
        todo.setCompletedAt(entity.getCompletedAt());
        return todo;
    }

    public static TodoEntity toEntity(Todo todo) {
        TodoEntity entity = new TodoEntity();
        entity.setId(todo.getId());
        entity.setTitle(todo.getTitle());
        entity.setDescription(todo.getDescription());
        entity.setCompleted(todo.isCompleted());
        entity.setCreatedAt(todo.getCreatedAt());
        entity.setUserId(todo.getUserId());
        entity.setPriority(todo.getPriority());
        entity.setDueDate(todo.getDueDate());
        entity.setListId(todo.getListId());
        entity.setTags(todo.getTags());
        entity.setCompletedAt(todo.getCompletedAt());
        return entity;
    }
}
