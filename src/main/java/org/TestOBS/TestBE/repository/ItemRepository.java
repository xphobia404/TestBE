package org.TestOBS.TestBE.repository;


import org.TestOBS.TestBE.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}