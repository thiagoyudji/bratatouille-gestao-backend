package br.com.bratatouille.management.customer.entity;

import br.com.bratatouille.management.auth.entity.AuthUser;
import jakarta.persistence.Column;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "customer_profiles")
public class CustomerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_user_id", nullable = false, unique = true)
    private AuthUser authUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 2)
    private CustomerType customerType;

    @Column(nullable = false, length = 160)
    private String fullName;

    @Column(nullable = false, length = 160)
    private String email;

    @Column(nullable = false, length = 30)
    private String phone;

    @ElementCollection
    @CollectionTable(name = "customer_profile_addresses", joinColumns = @JoinColumn(name = "customer_profile_id"))
    @OrderColumn(name = "address_order")
    private final List<CustomerAddress> addresses = new ArrayList<>();

    protected CustomerProfile() {
    }

    public CustomerProfile(
            AuthUser authUser,
            CustomerType customerType,
            String fullName,
            String email,
            String phone,
            List<CustomerAddress> addresses
    ) {
        if (authUser == null) {
            throw new IllegalArgumentException("authUser is required");
        }

        if (customerType == null) {
            throw new IllegalArgumentException("customerType is required");
        }

        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("fullName is required");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email is required");
        }

        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("phone is required");
        }

        this.authUser = authUser;
        this.customerType = customerType;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        if (addresses != null) {
            this.addresses.addAll(addresses);
        }
    }

    public Long getId() {
        return id;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public List<CustomerAddress> getAddresses() {
        return Collections.unmodifiableList(addresses);
    }

    public void update(
            CustomerType customerType,
            String fullName,
            String email,
            String phone,
            List<CustomerAddress> addresses
    ) {
        if (customerType == null) {
            throw new IllegalArgumentException("customerType is required");
        }

        if (customerType != this.customerType) {
            throw new IllegalArgumentException("customer type cannot be changed");
        }

        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("fullName is required");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email is required");
        }

        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("phone is required");
        }

        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.addresses.clear();
        if (addresses != null) {
            this.addresses.addAll(addresses);
        }
    }
}
