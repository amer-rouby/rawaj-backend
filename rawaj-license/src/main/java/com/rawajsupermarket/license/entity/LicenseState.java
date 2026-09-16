package com.rawajsupermarket.license.entity;

import com.rawajsupermarket.license.service.impl.LicenseServiceImpl;
import com.rawajsupermarket.common.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

// One row per store, created only once a renewal code has actually been applied
// (see LicenseServiceImpl) - a store with no row here is treated as unlimited,
// so shipping this feature never locks out a store nobody has licensed yet.
@Entity
@Table(name = "license_state", schema = "zaki_supermarket")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LicenseState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false, unique = true)
    private Store store;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    // Watermark that only ever moves forward (see LicenseServiceImpl.getStatus) -
    // if the system clock is ever seen behind this, the store is treated as
    // locked regardless of expiresAt, so rolling the clock back to before a
    // real expiry date can't bring an expired license back to "valid".
    @Column(name = "last_seen_at")
    private Instant lastSeenAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
