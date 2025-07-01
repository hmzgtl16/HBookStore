package org.example.hbookstore.user.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.NUMBER)
public enum UserStatus {
    ACTIVE,
    INACTIVE
}
