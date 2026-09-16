package com.rawajsupermarket.service;

import com.rawajsupermarket.dto.response.SendWhatsAppResponse;

public interface WhatsAppClientService {
    SendWhatsAppResponse sendMessage(String phoneNumber, String message);
}