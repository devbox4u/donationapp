package com.company.donationapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DonationAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(DonationAppApplication.class, args);
    }
}
