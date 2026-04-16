package org.acme.interfaces.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.application.dto.CreateSubtaskDto;
import org.acme.domain.models.Subtask;
import org.acme.domain.repository.SubtaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/todos/{todoId}/subtasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubtaskResource {

    private final SubtaskRepository subtaskRepository;

    @Inject
    public SubtaskResource(SubtaskRepository subtaskRepository) {
        this.subtaskRepository = subtaskRepository;
    }

    @GET
    public Response getSubtasks(@PathParam("todoId") UUID todoId) {
        List<Subtask> subtasks = subtaskRepository.findByTodoId(todoId);
        return Response.ok(subtasks).build();
    }

    @POST
    public Response createSubtask(@PathParam("todoId") UUID todoId, CreateSubtaskDto dto) {
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Title is required").build();
        }
        Subtask subtask = new Subtask();
        subtask.setId(UUID.randomUUID());
        subtask.setTitle(dto.getTitle().trim());
        subtask.setCompleted(false);
        subtask.setTodoId(todoId);
        subtask.setCreatedAt(LocalDateTime.now());
        Subtask saved = subtaskRepository.save(subtask);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PATCH
    @Path("/{subtaskId}/completed")
    public Response patchCompleted(
            @PathParam("todoId") UUID todoId,
            @PathParam("subtaskId") UUID subtaskId,
            Map<String, Boolean> body) {
        Boolean completed = body.get("completed");
        if (completed == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Missing 'completed' field").build();
        }
        boolean updated = subtaskRepository.updateCompleted(subtaskId, completed);
        return updated
                ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{subtaskId}")
    public Response deleteSubtask(
            @PathParam("todoId") UUID todoId,
            @PathParam("subtaskId") UUID subtaskId) {
        boolean deleted = subtaskRepository.deleteById(subtaskId);
        return deleted
                ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }
}
