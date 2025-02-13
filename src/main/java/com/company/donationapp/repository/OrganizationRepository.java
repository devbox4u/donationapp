package com.company.donationapp.repository;

import com.company.donationapp.domain.Organization;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {
    Optional<Organization> findByOrganizationId(UUID donorId);

    @Query("SELECT COUNT(do) > 0 FROM Donation do WHERE do.organization.id = :id")
    boolean hasDonations(@Param("id") Long id);
}
