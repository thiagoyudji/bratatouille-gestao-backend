package br.com.bratatouille.management.customer.dto;

import br.com.bratatouille.management.customer.entity.CustomerType;

import java.util.List;

public record CustomerProfileResponse(
        CustomerType customerType,
        String fullName,
        String email,
        String phone,
        List<CustomerAddressResponse> addresses
) {
}
