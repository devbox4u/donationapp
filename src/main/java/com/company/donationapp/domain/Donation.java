package com.company.donationapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "donation")
public class Donation extends Audit {

    protected Donation() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(unique = true)
    private UUID donationId;

    @ManyToOne(optional = false)
    private Donor donor;

    @ManyToOne(optional = false)
    private Organization organization;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDate donationDate;

    public static Donation createDonation(Donor donor, Organization organization, BigDecimal amount, LocalDate donationDate) {
        Donation donation = new Donation();
        donation.setDonationId(UUID.randomUUID());
        donation.setDonor(donor);
        donation.setOrganization(organization);
        donation.setAmount(amount);
        donation.setDonationDate(donationDate);
        return donation;
    }
}
