package com.company.donationapp.service;

import com.company.donationapp.controller.dto.DonorDTO;
import com.company.donationapp.domain.Donor;
import com.company.donationapp.exception.BusinessException;
import com.company.donationapp.exception.NotFoundException;
import com.company.donationapp.repository.DonorRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DonorService {

    @NonNull
    DonorRepository donorRepository;

    public List<DonorDTO> getAllDonors() {
        List<Donor> donors = new ArrayList<>();
        donorRepository.findAll().forEach(donors::add);

        return donors.stream()
                .map(this::toDonorDTO)
                .toList();
    }

    public DonorDTO getDonorByDonorId(UUID donorId) throws NotFoundException {
        return donorRepository.findByDonorId(donorId)
                .map(this::toDonorDTO)
                .orElseThrow(() -> new NotFoundException("Donor not found by donorId: " + donorId));
    }

    public UUID createDonor(DonorDTO dto) {
        Donor donor = Donor.createDonor();
        setDonorAttributes(dto, donor);
        donorRepository.save(donor);

        log.info("Created Donor: {}", donor.getDonorId());
        return donor.getDonorId();
    }

    public void updateDonor(UUID donorId, DonorDTO dto) throws NotFoundException {
        donorRepository.findByDonorId(donorId)
                .map(donor -> {
                    setDonorAttributes(dto, donor);
                    return donorRepository.save(donor);
                })
                .orElseThrow(() -> new NotFoundException("Donor not found"));

        log.info("Updated Donor: {}", donorId);
    }

    public void deleteDonor(UUID donorId) throws NotFoundException, BusinessException {
        Donor donor = donorRepository.findByDonorId(donorId)
                .orElseThrow(() -> new NotFoundException("Donor not found"));

        if (donorRepository.hasDonations(donor.getId())) {
            throw new BusinessException("Donor with donorId=" + donor.getDonorId() + " can not be deleted because of existing donations");
        }

        donorRepository.delete(donor);

        log.info("Deleted Donor: {}", donorId);
    }

    public Optional<Donor> findDonorById(UUID donorId) {
        return donorRepository.findByDonorId(donorId);
    }

    private DonorDTO toDonorDTO(Donor entity) {
        return DonorDTO.builder()
                .donorId(entity.getDonorId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .iban(entity.getIban())
                .street(entity.getStreet())
                .houseNumber(entity.getHouseNumber())
                .zipCode(entity.getZipCode())
                .city(entity.getCity())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .build();
    }

    private void setDonorAttributes(DonorDTO dto, Donor entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setIban(dto.getIban());
        entity.setStreet(dto.getStreet());
        entity.setHouseNumber(dto.getHouseNumber());
        entity.setZipCode(dto.getZipCode());
        entity.setCity(dto.getCity());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
    }
}
