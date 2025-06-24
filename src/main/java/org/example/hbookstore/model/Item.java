package org.example.hbookstore.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("order_items")
public class Item {
    @Id
    private Long id;
    private Long orderId;
    private Long bookId;
    private int quantity;
}