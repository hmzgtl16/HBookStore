package org.example.hbookstore.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table("orders")
public class Order {
    @Id
    private Long id;
    private Long userId;
    private LocalDateTime createdAt;
    private BigDecimal total;
}