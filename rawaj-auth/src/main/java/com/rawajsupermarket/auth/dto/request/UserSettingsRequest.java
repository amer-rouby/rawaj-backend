package com.rawajsupermarket.auth.dto.request;
import com.rawajsupermarket.auth.entity.Session;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSettingsRequest {
    @NotNull(message = "Session timeout is required")
    private Integer sessionTimeout;
}
