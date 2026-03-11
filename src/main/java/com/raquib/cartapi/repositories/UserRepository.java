package com.raquib.cartapi.repositories;

import com.raquib.cartapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
