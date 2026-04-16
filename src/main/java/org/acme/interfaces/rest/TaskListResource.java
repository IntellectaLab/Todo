package org.acme.interfaces.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.application.dto.CreateTaskListDto;
import org.acme.application.usecase.CreateTaskListUseCase;
import org.acme.domain.models.TaskList;
import org.acme.domain.repository.TaskListRepository;
import org.acme.infrastructure.security.AuthContext;

import java.util.List;
import java.util.UUID;

@Path("/lists")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskListResource {

    private final CreateTaskListUseCase createTaskListUseCase;
    private final TaskListRepository taskListRepository;
    private final AuthContext authContext;

    @Inject
    public TaskListResource(CreateTaskListUseCase createTaskListUseCase,
                            TaskListRepository taskListRepository,
                            AuthContext authContext) {
        this.createTaskListUseCase = createTaskListUseCase;
        this.taskListRepository = taskListRepository;
        this.authContext = authContext;
    }

    @GET
    public Response getLists() {
        UUID userId = authContext.getUser().getId();
        List<TaskList> lists = taskListRepository.findByUserId(userId);
        return Response.ok(lists).build();
    }

    @POST
    public Response createList(CreateTaskListDto dto) {
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El título es obligatorio").build();
        }
        TaskList list = createTaskListUseCase.execute(dto);
        return Response.status(Response.Status.CREATED).entity(list).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateList(@PathParam("id") UUID id, CreateTaskListDto dto) {
        TaskList updated = new TaskList();
        updated.setTitle(dto.getTitle());
        updated.setColor(dto.getColor());
        updated.setIcon(dto.getIcon());

        return taskListRepository.update(id, updated)
                .map(l -> Response.ok(l).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    public Response deleteList(@PathParam("id") UUID id) {
        boolean deleted = taskListRepository.deleteById(id);
        return deleted
                ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }
}
