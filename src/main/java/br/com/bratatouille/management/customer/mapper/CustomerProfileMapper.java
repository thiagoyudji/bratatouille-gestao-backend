package br.com.bratatouille.management.customer.mapper;

import br.com.bratatouille.management.customer.dto.CustomerAddressResponse;
import br.com.bratatouille.management.customer.dto.CustomerProfileResponse;
import br.com.bratatouille.management.customer.entity.CustomerAddress;
import br.com.bratatouille.management.customer.entity.CustomerProfile;
import org.springframework.stereotype.Component;

@Component
public class CustomerProfileMapper {

    public CustomerProfileResponse toResponse(CustomerProfile profile) {
        return new CustomerProfileResponse(
                profile.getCustomerType(),
                profile.getFullName(),
                profile.getEmail(),
                profile.getPhone(),
                profile.getAddresses().stream().map(this::toResponse).toList()
        );
    }

    private CustomerAddressResponse toResponse(CustomerAddress address) {
        return new CustomerAddressResponse(
                address.getLabel(),
                address.getZipCode(),
                address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getState(),
                address.getCity(),
                address.getComplement(),
                address.getDefaultAddress()
        );
    }
}
