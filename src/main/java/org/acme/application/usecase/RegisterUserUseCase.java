package org.acme.application.usecase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.application.dto.RegisterUserDto;
import org.acme.domain.models.User;
import org.acme.domain.repository.UserRepository;

import java.util.UUID;

@ApplicationScoped
public class RegisterUserUseCase {

    private final UserRepository userRepository;

    @Inject
    public RegisterUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // El usuario ya fue creado en Firebase por el frontend.
    // Aquí solo guardamos sus datos en MySQL usando el firebaseUuid que nos manda.
    public User execute(RegisterUserDto registerUserDto) {
        User user = new User();
        user.setEmail(registerUserDto.getEmail());
        user.setFullName(registerUserDto.getFullName());
        user.setRole("USER");
        user.setActive(true);
        user.setId(UUID.randomUUID());
        user.setFirebaseUuid(registerUserDto.getFirebaseUuid());
        return userRepository.create(user);
    }
}
