package com.rawajsupermarket.repository.license;

import com.rawajsupermarket.entity.license.LicenseState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LicenseStateRepository extends JpaRepository<LicenseState, Long> {
    Optional<LicenseState> findByStoreId(Long storeId);
}
