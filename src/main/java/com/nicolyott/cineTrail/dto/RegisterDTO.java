package com.nicolyott.cineTrail.dto;

import com.nicolyott.cineTrail.entity.user.UserRole;

public record RegisterDTO(
        String login,
        String password,
        UserRole role
) {
}
