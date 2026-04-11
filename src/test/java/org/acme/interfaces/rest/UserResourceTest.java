package org.acme.interfaces.rest;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.domain.models.User;
import org.acme.domain.repository.UserRepository;
import org.acme.infrastructure.firebase.FirebaseUserCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class UserResourceTest {

    @InjectMock
    FirebaseUserCreator firebaseUserCreator;

    @Inject
    UserRepository userRepository;

    // Password que cumple con: Mayúscula, Minúscula y Número
    private final String validPassword = "SafePassword123";

    @Test
    @DisplayName("1. Registro Exitoso: Debe persistir en H2 y Firebase")
    void registerUserShouldSucceed() throws FirebaseAuthException {
        String firebaseUid = "firebase-" + UUID.randomUUID();
        String email = "success-" + UUID.randomUUID() + "@test.com";

        UserRecord firebaseRecord = Mockito.mock(UserRecord.class);
        Mockito.when(firebaseRecord.getUid()).thenReturn(firebaseUid);
        Mockito.when(firebaseUserCreator.create(Mockito.eq(email), Mockito.anyString()))
                .thenReturn(firebaseRecord);

        String body = String.format(
            "{\"fullName\":\"Eduardo Abundiz\",\"email\":\"%s\",\"password\":\"%s\",\"passwordConfirm\":\"%s\"}",
            email, validPassword, validPassword
        );

        given()
                .contentType(JSON)
                .body(body)
                .when().post("/users")
                .then()
                .statusCode(200)
                .body("firebaseUuid", equalTo(firebaseUid))
                .body("email", equalTo(email));

        // Verificación en BD local (H2)
        assertTrue(userRepository.findByFirebaseUuid(firebaseUid).isPresent());
    }

    @Test
    @DisplayName("2. Validación: Las contraseñas deben coincidir")
    void registerUserWithMismatchedPasswordsShouldReturn400() {
        String body = "{"
            + "\"fullName\":\"Test User\","
            + "\"email\":\"mismatch@test.com\","
            + "\"password\":\"ValidPass123\","
            + "\"passwordConfirm\":\"DifferentPass123\""
            + "}";

        given()
                .contentType(JSON)
                .body(body)
                .when().post("/users")
                .then()
                .statusCode(400)
                .body("parameterViolations.message", hasItem("Las contraseñas no coinciden"));
    }

    @Test
    @DisplayName("3. Validación: Contraseña débil debe fallar (Regex)")
    void registerUserWithWeakPasswordShouldReturn400() {
        // "weak" no tiene mayúsculas ni números
        String body = "{"
            + "\"fullName\":\"Test User\","
            + "\"email\":\"weak@test.com\","
            + "\"password\":\"weak\","
            + "\"passwordConfirm\":\"weak\""
            + "}";

        given()
                .contentType(JSON)
                .body(body)
                .when().post("/users")
                .then()
                .statusCode(400)
                .body("parameterViolations.message", hasItem(containsString("debe contener al menos una minúscula, una mayúscula y un número")));
    }

    @Test
    @DisplayName("4. Validación: Email inválido")
    void registerUserWithInvalidEmailShouldReturn400() {
        String body = String.format(
            "{\"fullName\":\"Test\",\"email\":\"not-an-email\",\"password\":\"%s\",\"passwordConfirm\":\"%s\"}",
            validPassword, validPassword
        );

        given()
                .contentType(JSON)
                .body(body)
                .when().post("/users")
                .then()
                .statusCode(400)
                .body("parameterViolations.message", hasItem("El email debe tener un formato válido"));
    }

    @Test
    @DisplayName("5. Error de Sistema: Firebase fuera de línea")
    void registerUserShouldReturn500WhenFirebaseFails() throws FirebaseAuthException {
        Mockito.when(firebaseUserCreator.create(Mockito.anyString(), Mockito.anyString()))
                .thenThrow(new RuntimeException("Firebase connection failed"));

        String body = String.format(
            "{\"fullName\":\"Bob\",\"email\":\"bob@test.com\",\"password\":\"%s\",\"passwordConfirm\":\"%s\"}",
            validPassword, validPassword
        );

        given()
                .contentType(JSON)
                .body(body)
                .when().post("/users")
                .then()
                .statusCode(500);
    }
 
}