package com.rawajsupermarket.license.repository;

import com.rawajsupermarket.license.entity.LicenseState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LicenseStateRepository extends JpaRepository<LicenseState, Long> {
    Optional<LicenseState> findByStoreId(Long storeId);
}
