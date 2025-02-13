package com.company.donationapp.repository;

import com.company.donationapp.domain.Donor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@FieldDefaults(level = AccessLevel.PRIVATE)
class DonorRepositoryTest {

    @Autowired
    DonorRepository donorRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void whenRepositoryIsEmptyNoDonorWillFound() {
        Iterable<Donor> donor = donorRepository.findAll();
        assertThat(donor).isEmpty();
    }

    @Test
    void whenFindByIdThenExistingDonorWillBeReturned() {
        Donor donor = createDonor();
        UUID donorId = donor.getDonorId();

        entityManager.persist(donor);

        Optional<Donor> foundDonor = donorRepository.findById(donor.getId());

        assertTrue(foundDonor.isPresent());
        assertEquals(donorId, foundDonor.get().getDonorId());
    }

    @Test
    void whenFindByDonorIdThenExistingDonorWillBeReturned() {
        Donor donor = createDonor();
        UUID donorId = donor.getDonorId();

        entityManager.persist(donor);

        Optional<Donor> foundDonor = donorRepository.findByDonorId(donorId);

        assertTrue(foundDonor.isPresent());
    }

    @Test
    void whenFindAllDonorsThenExistingDonorsWillReturned() {
        Donor donor1 = createDonor();
        entityManager.persist(donor1);

        Donor donor2 = createDonor();
        entityManager.persist(donor2);

        Donor donor3 = createDonor();
        entityManager.persist(donor3);

        Iterable<Donor> donors = donorRepository.findAll();

        assertThat(donors).hasSize(3).contains(donor1, donor2, donor3);
    }

    private Donor createDonor() {
        Donor donor = Donor.createDonor();
        donor.setFirstName("Max");
        donor.setLastName("Mustermann");
        donor.setIban("DE02120300000000202051");
        donor.setStreet("Street");
        donor.setHouseNumber("1");
        donor.setZipCode("12345");
        donor.setCity("City");
        donor.setEmail("max.mustermann@gmail.com");
        donor.setPhoneNumber("123456789");
        return donor;
    }
}