package com.almod.repo;

import com.almod.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepo extends CrudRepository<Customer, Long> {
    List<Customer> findByNameStartsWithIgnoreCase(String name);

}
