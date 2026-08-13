package br.com.bratatouille.management.customer.dto;

public record CustomerAddressResponse(
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
}
