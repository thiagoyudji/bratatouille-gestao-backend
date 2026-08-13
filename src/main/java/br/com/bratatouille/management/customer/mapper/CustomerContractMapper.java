package br.com.bratatouille.management.customer.mapper;

import br.com.bratatouille.management.customer.dto.CustomerAddressRequest;
import br.com.bratatouille.management.customer.dto.CustomerAddressResponse;
import br.com.bratatouille.management.customer.dto.CustomerProfileRequest;
import br.com.bratatouille.management.customer.dto.CustomerProfileResponse;
import br.com.bratatouille.management.customer.entity.CustomerType;
import org.springframework.stereotype.Component;

@Component
public class CustomerContractMapper {

    public CustomerProfileRequest toDomainProfile(br.com.bratatouille.management.generated.model.CustomerProfileRequest request) {
        if (request == null) {
            return null;
        }

        return new CustomerProfileRequest(
                CustomerType.valueOf(request.getCustomerType().name()),
                request.getFullName(),
                request.getEmail(),
                request.getPhone(),
                request.getAddresses() == null ? null : request.getAddresses().stream().map(this::toDomainAddress).toList()
        );
    }

    public CustomerAddressRequest toDomainAddress(br.com.bratatouille.management.generated.model.CustomerAddressRequest request) {
        return new CustomerAddressRequest(
                request.getLabel(),
                request.getZipCode(),
                request.getStreet(),
                request.getNumber(),
                request.getNeighborhood(),
                request.getState(),
                request.getCity(),
                request.getComplement(),
                request.getDefaultAddress()
        );
    }

    public br.com.bratatouille.management.generated.model.CustomerProfileResponse toGeneratedProfile(CustomerProfileResponse response) {
        br.com.bratatouille.management.generated.model.CustomerProfileResponse generated =
                new br.com.bratatouille.management.generated.model.CustomerProfileResponse();

        generated.setCustomerType(br.com.bratatouille.management.generated.model.CustomerType.fromValue(response.customerType().name()));
        generated.setFullName(response.fullName());
        generated.setEmail(response.email());
        generated.setPhone(response.phone());
        generated.setAddresses(response.addresses().stream().map(this::toGeneratedAddress).toList());
        return generated;
    }

    public br.com.bratatouille.management.generated.model.CustomerAddressResponse toGeneratedAddress(CustomerAddressResponse response) {
        return new br.com.bratatouille.management.generated.model.CustomerAddressResponse()
                .label(response.label())
                .zipCode(response.zipCode())
                .street(response.street())
                .number(response.number())
                .neighborhood(response.neighborhood())
                .state(response.state())
                .city(response.city())
                .complement(response.complement())
                .defaultAddress(response.defaultAddress());
    }
}
