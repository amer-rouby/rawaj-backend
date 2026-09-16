package com.rawajsupermarket.settings.repository;

import com.rawajsupermarket.settings.entity.RawajFeatureSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RawajFeatureSettingsRepository extends JpaRepository<RawajFeatureSettings, Long> {

    @Query("SELECT s FROM RawajFeatureSettings s WHERE s.store.id = :storeId")
    Optional<RawajFeatureSettings> findByStoreId(@Param("storeId") Long storeId);
}
