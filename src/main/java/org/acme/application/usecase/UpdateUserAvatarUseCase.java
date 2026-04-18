package org.acme.application.usecase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.application.dto.UpdateUserAvatarDto;
import org.acme.domain.models.User;
import org.acme.domain.repository.UserRepository;

import java.util.UUID;

@ApplicationScoped
public class UpdateUserAvatarUseCase {

    private final UserRepository userRepository;

    @Inject
    public UpdateUserAvatarUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID userId, UpdateUserAvatarDto dto) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        user.setAvatarUrl(dto.getAvatarUrl());
        return userRepository.update(user);
    }
}
