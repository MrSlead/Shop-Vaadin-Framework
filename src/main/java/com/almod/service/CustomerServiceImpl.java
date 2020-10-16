package com.almod.service;

import com.almod.entity.Customer;
import com.almod.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepo customerRepo;

    @Autowired
    public void setCustomerRepo(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public Optional<Customer> getById(Long id) {
        return customerRepo.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return (List<Customer>) customerRepo.findAll();
    }

    @Override
    public void save(Customer customer) {
        customerRepo.save(customer);
    }

    @Override
    public void deleteById(Long id) {
        customerRepo.deleteById(id);
    }

    @Override
    public void delete(Customer customer) {
        customerRepo.delete(customer);
    }

    @Override
    public List<Customer> findByNameStartsWithIgnoreCase(String name) {
        return customerRepo.findByNameStartsWithIgnoreCase(name);
    }
}
