package org.acme.infrastructure.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.domain.models.Subtask;
import org.acme.domain.repository.SubtaskRepository;
import org.acme.infrastructure.entities.SubtaskEntity;
import org.acme.infrastructure.mapper.SubtaskMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class SubtaskRepositoryImpl implements SubtaskRepository, PanacheRepositoryBase<SubtaskEntity, UUID> {

    @Override
    @Transactional
    public Subtask save(Subtask subtask) {
        SubtaskEntity entity = SubtaskMapper.toEntity(subtask);
        persist(entity);
        return SubtaskMapper.toDomain(entity);
    }

    @Override
    public List<Subtask> findByTodoId(UUID todoId) {
        return find("todoId", todoId)
                .stream()
                .map(SubtaskMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Subtask> findSubtaskById(UUID id) {
        return findByIdOptional(id).map(SubtaskMapper::toDomain);
    }

    @Override
    @Transactional
    public boolean updateCompleted(UUID id, boolean completed) {
        return update("completed = ?1 where id = ?2", completed, id) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(UUID id) {
        return delete("id", id) > 0;
    }
}
