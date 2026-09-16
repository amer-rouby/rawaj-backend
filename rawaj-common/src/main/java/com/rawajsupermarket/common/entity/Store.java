package com.rawajsupermarket.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stores")
@Where(clause = "deleted_at IS NULL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(unique = true, length = 100)
    private String licenseNumber;

    // System-generated, globally unique (unlike `id`, which is a per-database
    // auto-increment counter - every customer runs their own separate,
    // offline database, so every customer's FIRST store would otherwise get
    // id=1, and an activation code bound to that id would unlock every other
    // customer's first store too). This is what activation codes are bound to.
    @Column(name = "license_key", unique = true, length = 36)
    @Builder.Default
    private String licenseKey = java.util.UUID.randomUUID().toString();

    @Column(length = 500)
    private String address;

    @Column(length = 20)
    private String phone;

    @Column(unique = true, length = 255)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SubscriptionStatus subscriptionStatus = SubscriptionStatus.TRIAL;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private PlanType planType = PlanType.BASIC;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnoreProperties({"store"})
    private List<User> users = new ArrayList<>();

    public enum SubscriptionStatus {
        TRIAL, ACTIVE, SUSPENDED, CANCELLED
    }

    public enum PlanType {
        BASIC, PROFESSIONAL, ENTERPRISE
    }
}
