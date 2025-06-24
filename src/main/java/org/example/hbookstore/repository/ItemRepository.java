package org.example.hbookstore.repository;

import org.example.hbookstore.model.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
}
