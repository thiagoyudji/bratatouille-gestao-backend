package br.com.bratatouille.management.customer.controller;

import br.com.bratatouille.management.customer.dto.CustomerAddressResponse;
import br.com.bratatouille.management.customer.dto.CustomerProfileResponse;
import br.com.bratatouille.management.customer.mapper.CustomerContractMapper;
import br.com.bratatouille.management.customer.service.CustomerProfileService;
import br.com.bratatouille.management.generated.api.CustomersApiDelegate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerApiDelegateImpl implements CustomersApiDelegate {

    private final CustomerProfileService customerProfileService;
    private final CustomerContractMapper customerContractMapper;

    public CustomerApiDelegateImpl(CustomerProfileService customerProfileService, CustomerContractMapper customerContractMapper) {
        this.customerProfileService = customerProfileService;
        this.customerContractMapper = customerContractMapper;
    }

    @Override
    public ResponseEntity<br.com.bratatouille.management.generated.model.CustomerProfileResponse> getMyCustomerProfile() {
        return ResponseEntity.ok(customerContractMapper.toGeneratedProfile(customerProfileService.getMyProfile()));
    }

    @Override
    public ResponseEntity<br.com.bratatouille.management.generated.model.CustomerProfileResponse> updateMyCustomerProfile(
            br.com.bratatouille.management.generated.model.CustomerProfileRequest request
    ) {
        return ResponseEntity.ok(customerContractMapper.toGeneratedProfile(
                customerProfileService.updateMyProfile(customerContractMapper.toDomainProfile(request))
        ));
    }
}
