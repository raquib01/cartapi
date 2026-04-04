package com.raquib.cartapi.repositories;

import com.raquib.cartapi.entities.Order;
import com.raquib.cartapi.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @EntityGraph(attributePaths = "items.product")
    @Query("SELECT o FROM Order o WHERE o.user = :user")
    List<Order> getOrderByUser(@Param("user") User user);


    @EntityGraph(attributePaths = "items.product")
    @Query("SELECT o FROM Order o WHERE o.id = :orderId")
    Optional<Order> getOrderById(UUID orderId);
}
