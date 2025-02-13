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
@Table(name = "donor")
public class Donor extends Audit {

    protected Donor() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(unique = true)
    private UUID donorId;

    @NotNull
    @Column(name = "firstname")
    private String firstName;

    @NotNull
    @Column(name = "lastname")
    private String lastName;

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

    public static Donor createDonor() {
        Donor donor = new Donor();
        donor.setDonorId(UUID.randomUUID());
        return donor;
    }
}
