package com.company.donationapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "organization")
public class Organization extends Audit {

    protected Organization() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(unique = true)
    private UUID organizationId;

    @NotNull
    private String name;

    @NotNull
    private String iban;

    @NotNull
    private String street;

    @NotNull
    private String houseNumber;

    @NotNull
    private String zipCode;

    @NotNull
    private String city;

    @NotNull
    private String email;

    private String phoneNumber;

    public static Organization createOrganization() {
        Organization organization = new Organization();
        organization.setOrganizationId(UUID.randomUUID());
        return organization;
    }
}
