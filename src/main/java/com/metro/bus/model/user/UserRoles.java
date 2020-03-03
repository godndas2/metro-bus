package com.metro.bus.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRoles {

    PASSENGER("ROLE_PASSENGER", "승객"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}
