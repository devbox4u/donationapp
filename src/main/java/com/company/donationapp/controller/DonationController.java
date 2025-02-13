package com.company.donationapp.controller;

import com.company.donationapp.controller.dto.DonationDTO;
import com.company.donationapp.exception.NotFoundException;
import com.company.donationapp.service.DonationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/donation")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Donation API", description = "Management of Donations")
public class DonationController {

    @NonNull
    DonationService donationService;

    @Operation(summary = "Returns a list of donations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of donations is returned", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DonationDTO.class))})}
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DonationDTO>> getAllDonations() {
        return ResponseEntity.ok(donationService.getAllDonations());
    }

    @Operation(summary = "Returns a donation by its donation id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Donation details are returned", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DonationDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Donation not found")}
    )
    @GetMapping(path = "/{donationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DonationDTO> getDonationById(@Parameter(in = ParameterIn.PATH, description = "UUID of donation") @PathVariable UUID donationId) {
        try {
            return ResponseEntity.ok(donationService.getDonationByDonationId(donationId));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Operation(summary = "Creates a donation")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Donation details to add", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = DonationDTO.class))})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Donation created and generated UUID is returned")}
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> createDonation(@RequestBody @Valid DonationDTO dto) {
        try {
            UUID donationId = donationService.createDonation(dto);
            return ResponseEntity.ok(donationId);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
