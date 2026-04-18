package org.acme.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateUserAvatarDto {

    @NotBlank(message = "La URL del avatar no puede estar vacía")
    @Size(max = 500, message = "La URL del avatar no puede exceder 500 caracteres")
    private String avatarUrl;

    public UpdateUserAvatarDto() {
    }

    public UpdateUserAvatarDto(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
