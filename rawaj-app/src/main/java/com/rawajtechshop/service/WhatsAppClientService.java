package com.rawajtechshop.service;

import com.rawajtechshop.purchasing.dto.response.SendWhatsAppResponse;

public interface WhatsAppClientService {
    SendWhatsAppResponse sendMessage(String phoneNumber, String message);
}
