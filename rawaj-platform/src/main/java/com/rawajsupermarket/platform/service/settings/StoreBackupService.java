package com.rawajsupermarket.platform.service.settings;

import com.rawajsupermarket.settings.dto.request.BackupRequest;
import com.rawajsupermarket.settings.dto.response.BackupResponse;
import com.rawajsupermarket.settings.service.BackupService;

import java.util.List;

/**
 * Self-service, per-store data export. Unlike BackupService (whole-database,
 * pg_dump-based, platform-operator only), this is a JSON export of just the calling
 * store's own rows - pg_dump can't filter by tenant, so this queries each
 * store-scoped table directly instead. Safe by construction: it only ever reads
 * and writes a file, it never touches the database, so unlike a restore there's no
 * cross-tenant risk here.
 */
public interface StoreBackupService {

    BackupResponse createBackup(Long storeId, Long userId, BackupRequest request);

    List<BackupResponse> getBackupsForStore(Long storeId);

    BackupResponse getBackup(Long id, Long storeId);

    byte[] downloadBackup(Long id, Long storeId);

    void deleteBackup(Long id, Long storeId);
}
