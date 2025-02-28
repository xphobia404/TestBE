package org.TestOBS.TestBE.repository;

import org.TestOBS.TestBE.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
