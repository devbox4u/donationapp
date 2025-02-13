package com.company.donationapp.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class OrganizationDTO {

    UUID organizationId;

    @NotBlank
    String name;

    @NotBlank
    String iban;

    @NotBlank
    String street;

    @NotBlank
    String houseNumber;

    @NotBlank
    String zipCode;

    @NotBlank
    String city;

    @NotBlank
    String email;

    String phoneNumber;
}