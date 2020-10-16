package com.almod.service;

import com.almod.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Optional<Customer> getById(Long id);

    List<Customer> findAll();

    void save(Customer customer);

    void deleteById(Long id);

    void delete(Customer customer);

    List<Customer> findByNameStartsWithIgnoreCase(String name);
}
