package com.almod;

import com.almod.enginer.Gender;
import com.almod.entity.Customer;
import com.almod.repo.CustomerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;

@SpringBootApplication
public class ShopApplication {
    private static final Logger log = LoggerFactory.getLogger(ShopApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(CustomerRepo repository) {
        return (args) -> {
            // save customers in the database
            repository.save(new Customer("Adam", Gender.MALE, new Date()));
            repository.save(new Customer("Eva", Gender.FEMALE, new Date()));

            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Customer customer : repository.findAll()) {
                log.info(customer.toString());
            }
        };
    }
}
