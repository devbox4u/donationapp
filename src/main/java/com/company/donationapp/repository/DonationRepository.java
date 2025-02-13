package com.company.donationapp.repository;

import com.company.donationapp.domain.Donation;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface DonationRepository extends CrudRepository<Donation, Long> {
    Optional<Donation> findByDonationId(UUID donationId);
}
