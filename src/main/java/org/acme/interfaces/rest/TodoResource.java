package org.acme.interfaces.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.application.dto.CreateTodoDto;
import org.acme.application.dto.UpdateTodoDto;
import org.acme.application.usecase.CreateTodoUseCase;
import org.acme.domain.models.Todo;
import org.acme.domain.repository.TodoRepository;
import org.acme.infrastructure.security.AuthContext;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/todos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

    private final CreateTodoUseCase createTodoUseCase;
    private final TodoRepository todoRepository;
    private final AuthContext authContext;

    @Inject
    public TodoResource(CreateTodoUseCase createTodoUseCase,
                        TodoRepository todoRepository,
                        AuthContext authContext) {
        this.createTodoUseCase = createTodoUseCase;
        this.todoRepository = todoRepository;
        this.authContext = authContext;
    }

    @GET
    public Response getAllTodos() {
        UUID userId = authContext.getUser().getId();
        List<Todo> todos = todoRepository.findByUserId(userId);
        return Response.ok(todos).build();
    }

    @POST
    public Response createTodo(CreateTodoDto dto) {
        Todo todo = createTodoUseCase.execute(dto);
        return Response.status(Response.Status.CREATED).entity(todo).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateTodo(@PathParam("id") UUID id, UpdateTodoDto dto) {
        Todo updated = new Todo();
        updated.setTitle(dto.getTitle());
        updated.setDescription(dto.getDescription());
        updated.setPriority(dto.getPriority());
        updated.setDueDate(dto.getDueDate());
        updated.setListId(dto.getListId());
        updated.setTags(dto.getTags());

        return todoRepository.update(id, updated)
                .map(t -> Response.ok(t).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        boolean deleted = todoRepository.deleteById(id);
        return deleted
                ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @PATCH
    @Path("/{id}/completed")
    public Response patchCompleted(@PathParam("id") UUID id, Map<String, Boolean> body) {
        Boolean completed = body.get("completed");
        if (completed == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Missing 'completed' field").build();
        }
        boolean updated = todoRepository.updateCompleted(id, completed);
        return updated
                ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }
}
