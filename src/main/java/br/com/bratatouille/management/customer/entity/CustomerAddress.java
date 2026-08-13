package br.com.bratatouille.management.customer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CustomerAddress {

    @Column(length = 80)
    private String label;

    @Column(length = 20)
    private String zipCode;

    @Column(length = 120)
    private String street;

    @Column(length = 40)
    private String number;

    @Column(length = 120)
    private String neighborhood;

    @Column(length = 2)
    private String state;

    @Column(length = 120)
    private String city;

    @Column(length = 120)
    private String complement;

    @Column(nullable = false)
    private Boolean defaultAddress = false;

    protected CustomerAddress() {
    }

    public CustomerAddress(
            String label,
            String zipCode,
            String street,
            String number,
            String neighborhood,
            String state,
            String city,
            String complement,
            Boolean defaultAddress
    ) {
        this.label = label;
        this.zipCode = zipCode;
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.state = state;
        this.city = city;
        this.complement = complement;
        this.defaultAddress = Boolean.TRUE.equals(defaultAddress);
    }

    public String getLabel() {
        return label;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getComplement() {
        return complement;
    }

    public Boolean getDefaultAddress() {
        return defaultAddress;
    }
}
