package com.kasperovich.desoccer.dto.auth;

import com.kasperovich.desoccer.enums.Roles;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponse {

    String token;

    String login;

    String email;

    Roles roleName;

}
