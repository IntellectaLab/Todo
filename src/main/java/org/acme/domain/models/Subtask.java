package org.acme.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Subtask {
    private UUID id;
    private String title;
    private boolean completed;
    private UUID todoId;
    private LocalDateTime createdAt;

    public Subtask() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public UUID getTodoId() { return todoId; }
    public void setTodoId(UUID todoId) { this.todoId = todoId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
