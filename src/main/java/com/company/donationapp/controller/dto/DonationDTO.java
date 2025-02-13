package com.company.donationapp.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
public class DonationDTO {

    UUID donationId;

    @NotNull
    UUID donorId;

    @NotNull
    UUID organizationId;

    @NotNull
    BigDecimal amount;

    @NotNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    LocalDate donationDate;

    String donorIban;
    String organizationIban;
    String fullNameOfDonor;
    String organizationName;
}