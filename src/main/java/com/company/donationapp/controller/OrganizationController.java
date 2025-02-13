package com.company.donationapp.controller;

import com.company.donationapp.controller.dto.OrganizationDTO;
import com.company.donationapp.exception.BusinessException;
import com.company.donationapp.exception.NotFoundException;
import com.company.donationapp.service.OrganizationService;
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
@RequestMapping(path = "/organization")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Organization API", description = "Management of Organizations")
public class OrganizationController {

    @NonNull
    OrganizationService organizationService;

    @Operation(summary = "Returns a list of organizations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of organizations is returned", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrganizationDTO.class))})}
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrganizationDTO>> getAllOrganizations() {
        return ResponseEntity.ok(organizationService.getAllOrganizations());
    }


    @Operation(summary = "Returns an organization by its organization id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Organization details are returned", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = OrganizationDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Organization not found")}
    )
    @GetMapping(path = "/{organizationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizationDTO> getOrganizationById(@Parameter(in = ParameterIn.PATH, description = "UUID of organization") @PathVariable UUID organizationId) {
        try {
            return ResponseEntity.ok(organizationService.getOrganizationByOrganizationId(organizationId));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Operation(summary = "Creates an organization")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Organization details to add", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = OrganizationDTO.class))})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Organization created and generated UUID is returned")}
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> createOrganization(@RequestBody @Valid OrganizationDTO dto) {
        UUID organizationId = organizationService.createOrganization(dto);

        return ResponseEntity.ok(organizationId);
    }

    @Operation(summary = "Updates an organization")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Organization details to update", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = OrganizationDTO.class))})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Organization updated"),
            @ApiResponse(responseCode = "404", description = "Organization not found")}
    )
    @PutMapping(path = "/{organizationId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateOrganization(@Parameter(in = ParameterIn.PATH, description = "UUID of organization") @PathVariable UUID organizationId, @RequestBody @Valid OrganizationDTO dto) {
        try {
            organizationService.updateOrganization(organizationId, dto);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Deletes an organization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Organization deleted"),
            @ApiResponse(responseCode = "400", description = "Organization can not be deleted"),
            @ApiResponse(responseCode = "404", description = "Organization not found")}
    )
    @DeleteMapping(path = "/{organizationId}")
    public ResponseEntity<Void> deleteOrganization(@Parameter(in = ParameterIn.PATH, description = "UUID of organization") @PathVariable UUID organizationId) {
        try {
            organizationService.deleteOrganization(organizationId);
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
