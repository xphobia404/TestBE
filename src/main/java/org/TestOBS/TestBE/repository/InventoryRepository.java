package org.TestOBS.TestBE.repository;

import org.TestOBS.TestBE.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}