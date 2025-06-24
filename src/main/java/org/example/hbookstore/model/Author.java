package org.example.hbookstore.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "books")
public class Author {

    @Id
    private Long id;

    private String name;
}
