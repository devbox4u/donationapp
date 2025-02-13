package com.company.donationapp.controller;

import com.company.donationapp.controller.dto.DonorDTO;
import com.company.donationapp.exception.BusinessException;
import com.company.donationapp.exception.NotFoundException;
import com.company.donationapp.service.DonorService;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/donor")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Donor API", description = "Management of Donors")
public class DonorController {

    @NonNull
    DonorService donorService;

    @Operation(summary = "Returns a list of donors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of donors is returned", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DonorDTO.class))})}
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DonorDTO>> getAllDonors() {
        return ResponseEntity.ok(donorService.getAllDonors());
    }


    @Operation(summary = "Returns a donor by its donor id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Donor details are returned", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DonorDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Donor not found")}
    )
    @GetMapping(path = "/{donorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DonorDTO> getDonorById(@Parameter(in = ParameterIn.PATH, description = "UUID of donor") @PathVariable UUID donorId) {
        try {
            return ResponseEntity.ok(donorService.getDonorByDonorId(donorId));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Operation(summary = "Creates a donor")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Donor details to add", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = DonorDTO.class))})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Donor created and generated UUID is returned")}
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> createDonor(@RequestBody @Valid DonorDTO dto) {
        UUID donorId = donorService.createDonor(dto);

        return ResponseEntity.ok(donorId);
    }

    @Operation(summary = "Updates a donor")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Donor details to update", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = DonorDTO.class))})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Donor updated"),
            @ApiResponse(responseCode = "404", description = "Donor not found")}
    )
    @PutMapping(path = "/{donorId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateDonor(@Parameter(in = ParameterIn.PATH, description = "UUID of donor") @PathVariable UUID donorId, @RequestBody @Valid DonorDTO dto) {
        try {
            donorService.updateDonor(donorId, dto);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Deletes a donor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Donor deleted"),
            @ApiResponse(responseCode = "400", description = "Donor can not be deleted"),
            @ApiResponse(responseCode = "404", description = "Donor not found")}
    )
    @DeleteMapping(path = "/{donorId}")
    public ResponseEntity<Void> deleteDonor(@Parameter(in = ParameterIn.PATH, description = "UUID of donor") @PathVariable UUID donorId) {
        try {
            donorService.deleteDonor(donorId);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (BusinessException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
