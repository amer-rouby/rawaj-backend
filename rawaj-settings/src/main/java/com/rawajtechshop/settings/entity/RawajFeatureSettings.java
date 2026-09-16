package com.rawajtechshop.settings.entity;

import com.rawajtechshop.common.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

// Per-store on/off switches for the Rawaj AI feature set. Store-scoped (not
// per-user) since these are store-wide capabilities an ADMIN turns on/off,
// mirroring StoreSettings rather than the per-user NotificationSettings.
@Entity
@Table(name = "zaki_feature_settings", schema = "zaki_supermarket")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawajFeatureSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false, unique = true)
    private Store store;

    @Builder.Default
    private Boolean stockPredictionEnabled = true;

    @Builder.Default
    private Boolean reorderRecommendationsEnabled = true;

    @Builder.Default
    private Boolean pricingRecommendationsEnabled = true;

    @Builder.Default
    private Boolean supplierRecommendationsEnabled = true;

    @Builder.Default
    private Boolean dashboardInsightsEnabled = true;

    @Builder.Default
    private Boolean dailyBriefEnabled = true;

    @Builder.Default
    private Boolean anomalyDetectionEnabled = true;

    @Builder.Default
    private Boolean realtimeUpdatesEnabled = true;

    @Builder.Default
    private Boolean voiceSearchEnabled = true;

    @Builder.Default
    private Boolean customerCreditEnabled = true;

    @Builder.Default
    private Boolean aiAssistantEnabled = true;

    // Off by default - not functional until real ETA credentials are configured.
    @Builder.Default
    private Boolean eInvoiceEnabled = false;

    // Off by default - offline queueing changes POS behavior in ways an admin
    // should opt into deliberately, not something silently on from day one.
    @Builder.Default
    private Boolean offlineModeEnabled = false;

    // Off by default - not functional until real SMTP credentials (MAIL_USERNAME/
    // MAIL_PASSWORD) are configured, same rationale as eInvoiceEnabled above.
    @Builder.Default
    private Boolean emailEnabled = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
