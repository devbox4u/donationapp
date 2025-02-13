package com.company.donationapp.service;

import com.company.donationapp.controller.dto.DonorDTO;
import com.company.donationapp.domain.Donor;
import com.company.donationapp.exception.NotFoundException;
import com.company.donationapp.repository.DonorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DonorServiceTest {

    @Mock
    DonorRepository donorRepository;
    @InjectMocks
    DonorService donorService;

    @Test
    void whenDonorFoundThenDonorDTOIsReturned() throws NotFoundException {
        Donor donor = createDonor();

        when(donorRepository.findByDonorId(any(UUID.class))).thenReturn(Optional.of(donor));

        DonorDTO donorDTO = donorService.getDonorByDonorId(donor.getDonorId());

        assertNotNull(donorDTO);
        assertDonorEqualsDonorDTO(donor, donorDTO);
    }

    @Test
    void whenDonorNotFoundThenExceptionWillBeThrown() {
        UUID donorId = UUID.randomUUID();

        when(donorRepository.findByDonorId(donorId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> donorService.getDonorByDonorId(donorId));
    }

    @Test
    void whenDonorIdIsFoundThenDonorByIdWillBeReturned() {
        Donor donor = createDonor();
        UUID donorId = donor.getDonorId();

        when(donorRepository.findByDonorId(any(UUID.class))).thenReturn(Optional.of(donor));

        Optional<Donor> result = donorService.findDonorById(donorId);

        assertTrue(result.isPresent());
        assertEquals(donor, result.get());
        verify(donorRepository, times(1)).findByDonorId(any(UUID.class));
    }


    @Test
    void whenDonorNotFoundThenOptionalEmptyIsReturned() {
        UUID donorId = UUID.randomUUID();

        when(donorRepository.findByDonorId(donorId)).thenReturn(Optional.empty());

        Optional<Donor> result = donorService.findDonorById(donorId);

        assertEquals(Optional.empty(), result);
        verify(donorRepository, times(1)).findByDonorId(donorId);
    }


    @Test
    void whenUpdateDonorSucceed() throws NotFoundException {
        Donor existingDonor = createDonor();
        UUID donorId = existingDonor.getDonorId();
        when(donorRepository.findByDonorId(any(UUID.class))).thenReturn(Optional.of(existingDonor));

        Donor updatedDonor = createDonor();
        updatedDonor.setDonorId(donorId);
        updatedDonor.setIban("DE51370205000007808005");
        when(donorRepository.save(any(Donor.class))).thenReturn(updatedDonor);

        DonorDTO updateDonorDTO = createDonorDTO(donorId);

        donorService.updateDonor(donorId, updateDonorDTO);

        verify(donorRepository, times(1)).findByDonorId(donorId);
        verify(donorRepository, times(1)).save(existingDonor);

        Donor donorToCheck = donorRepository.findByDonorId(donorId).orElseThrow();
        assertEquals(updatedDonor.getIban(), donorToCheck.getIban());
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

    private DonorDTO createDonorDTO(UUID donorId) {
        return DonorDTO.builder()
                .donorId(donorId)
                .firstName("Max")
                .lastName("Mustermann")
                .iban("DE51370205000007808005")
                .street("Street")
                .houseNumber("1")
                .zipCode("12345")
                .city("City")
                .email("max.mustermann@gmail.com")
                .phoneNumber("123456789")
                .build();
    }

    private void assertDonorEqualsDonorDTO(Donor donor, DonorDTO dto) {
        assertEquals(donor.getDonorId(), dto.getDonorId());
        assertEquals(donor.getFirstName(), dto.getFirstName());
        assertEquals(donor.getLastName(), dto.getLastName());
        assertEquals(donor.getIban(), dto.getIban());
        assertEquals(donor.getStreet(), dto.getStreet());
        assertEquals(donor.getHouseNumber(), dto.getHouseNumber());
        assertEquals(donor.getZipCode(), dto.getZipCode());
        assertEquals(donor.getCity(), dto.getCity());
        assertEquals(donor.getEmail(), dto.getEmail());
        assertEquals(donor.getPhoneNumber(), dto.getPhoneNumber());
    }
}