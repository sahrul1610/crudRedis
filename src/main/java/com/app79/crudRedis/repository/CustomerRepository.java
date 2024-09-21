package com.app79.crudRedis.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app79.crudRedis.model.Customer;



public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByName(String name);
}
