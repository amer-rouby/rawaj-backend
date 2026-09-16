package com.rawajsupermarket.settings.service;

import com.rawajsupermarket.settings.dto.request.BackupRequest;
import com.rawajsupermarket.settings.dto.response.BackupResponse;

import java.util.List;

public interface BackupService {

    BackupResponse createBackup(BackupRequest request, Long userId);

    List<BackupResponse> getAllBackups();

    BackupResponse getBackupById(Long id);

    void deleteBackup(Long id);

    void restoreBackup(Long id);

    byte[] downloadBackup(Long id);
}
