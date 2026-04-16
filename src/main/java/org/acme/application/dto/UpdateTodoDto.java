package org.acme.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public class UpdateTodoDto {
    private String title;
    private String description;
    private String priority;
    private LocalDate dueDate;
    private UUID listId;
    private String tags;

    public UpdateTodoDto() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public UUID getListId() { return listId; }
    public void setListId(UUID listId) { this.listId = listId; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
}
