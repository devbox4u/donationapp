package com.company.donationapp.repository;

import com.company.donationapp.domain.Donor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface DonorRepository extends CrudRepository<Donor, Long> {
    Optional<Donor> findByDonorId(UUID donorId);

    @Query("SELECT COUNT(do) > 0 FROM Donation do WHERE do.donor.id = :id")
    boolean hasDonations(@Param("id") Long id);
}
