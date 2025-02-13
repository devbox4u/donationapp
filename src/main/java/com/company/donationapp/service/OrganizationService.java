package com.company.donationapp.service;

import com.company.donationapp.controller.dto.OrganizationDTO;
import com.company.donationapp.domain.Organization;
import com.company.donationapp.exception.BusinessException;
import com.company.donationapp.exception.NotFoundException;
import com.company.donationapp.repository.OrganizationRepository;
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
public class OrganizationService {

    @NonNull
    OrganizationRepository organizationRepository;

    public List<OrganizationDTO> getAllOrganizations() {
        List<Organization> organizations = new ArrayList<>();
        organizationRepository.findAll().forEach(organizations::add);

        return organizations.stream()
                .map(this::toOrganizationDTO)
                .toList();
    }

    public OrganizationDTO getOrganizationByOrganizationId(UUID organizationId) throws NotFoundException {
        return organizationRepository.findByOrganizationId(organizationId)
                .map(this::toOrganizationDTO)
                .orElseThrow(() -> new NotFoundException("Organization not found by organizationId: " + organizationId));
    }

    public UUID createOrganization(OrganizationDTO dto) {
        Organization organization = Organization.createOrganization();
        setOrganizationAttributes(dto, organization);
        organizationRepository.save(organization);

        log.info("Created Organization: {}", organization.getOrganizationId());
        return organization.getOrganizationId();
    }

    public void updateOrganization(UUID organizationId, OrganizationDTO dto) throws NotFoundException {
        organizationRepository.findByOrganizationId(organizationId)
                .map(organization -> {
                    setOrganizationAttributes(dto, organization);
                    return organizationRepository.save(organization);
                })
                .orElseThrow(() -> new NotFoundException("Organization not found"));

        log.info("Updated Organization: {}", organizationId);
    }

    public void deleteOrganization(UUID organizationId) throws NotFoundException, BusinessException {
        Organization organization = organizationRepository.findByOrganizationId(organizationId)
                .orElseThrow(() -> new NotFoundException("Organization not found"));

        if (organizationRepository.hasDonations(organization.getId())) {
            throw new BusinessException("Organization with organizationId=" + organization.getOrganizationId() + " can not be deleted because of existing donations");
        }
        organizationRepository.delete(organization);

        log.info("Deleted Organization: {}", organizationId);
    }

    public Optional<Organization> findByOrganizationId(UUID organizationId) {
        return organizationRepository.findByOrganizationId(organizationId);
    }

    private OrganizationDTO toOrganizationDTO(Organization entity) {
        return OrganizationDTO.builder()
                .organizationId(entity.getOrganizationId())
                .name(entity.getName())
                .iban(entity.getIban())
                .street(entity.getStreet())
                .houseNumber(entity.getHouseNumber())
                .zipCode(entity.getZipCode())
                .city(entity.getCity())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .build();
    }

    private void setOrganizationAttributes(OrganizationDTO dto, Organization entity) {
        entity.setName(dto.getName());
        entity.setIban(dto.getIban());
        entity.setStreet(dto.getStreet());
        entity.setHouseNumber(dto.getHouseNumber());
        entity.setZipCode(dto.getZipCode());
        entity.setCity(dto.getCity());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
    }
}