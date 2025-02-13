package com.company.donationapp.service;

import com.company.donationapp.controller.dto.DonationDTO;
import com.company.donationapp.domain.Donation;
import com.company.donationapp.domain.Donor;
import com.company.donationapp.domain.Organization;
import com.company.donationapp.exception.NotFoundException;
import com.company.donationapp.repository.DonationRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DonationService {

    @NonNull
    DonationRepository donationRepository;

    @NonNull
    DonorService donorService;

    @NonNull
    OrganizationService organizationService;

    public List<DonationDTO> getAllDonations() {
        List<Donation> donations = new ArrayList<>();
        donationRepository.findAll().forEach(donations::add);

        return donations.stream()
                .map(this::toDonationDTO)
                .toList();
    }

    public DonationDTO getDonationByDonationId(UUID donationId) throws NotFoundException {
        return donationRepository.findByDonationId(donationId)
                .map(this::toDonationDTO)
                .orElseThrow(() -> new NotFoundException("Donation not found by donationId: " + donationId));
    }

    public UUID createDonation(DonationDTO dto) throws NotFoundException {
        Donor donor = donorService.findDonorById(dto.getDonorId()).orElseThrow(() -> new NotFoundException("Donor not found by donorId: " + dto.getDonorId()));
        Organization organization = organizationService.findByOrganizationId(dto.getOrganizationId()).orElseThrow(() -> new NotFoundException("Organization not found by organizationId: " + dto.getOrganizationId()));

        Donation donation = Donation.createDonation(donor, organization, dto.getAmount(), dto.getDonationDate());
        donationRepository.save(donation);

        log.info("Created Donation: {} Donor: {} Organization: {}", donation.getDonationId(), donor.getDonorId(), organization.getOrganizationId());
        return donation.getDonationId();
    }

    private DonationDTO toDonationDTO(Donation entity) {
        Donor donor = entity.getDonor();
        Organization organization = entity.getOrganization();

        return DonationDTO.builder()
                .donationId(entity.getDonationId())
                .amount(entity.getAmount())
                .donationDate(entity.getDonationDate())
                .donorId(donor.getDonorId())
                .organizationId(organization.getOrganizationId())
                .fullNameOfDonor(donor.getFirstName() + " " + donor.getLastName())
                .organizationName(organization.getName())
                .donorIban(donor.getIban())
                .organizationIban(organization.getIban())
                .build();
    }
}
