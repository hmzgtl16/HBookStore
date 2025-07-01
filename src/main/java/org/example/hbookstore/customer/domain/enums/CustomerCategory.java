package org.example.hbookstore.customer.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.NUMBER)
public enum CustomerCategory {
    REGULAR,
    PREMIUM,
    ENTERPRISE
}
