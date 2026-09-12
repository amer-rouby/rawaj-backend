package com.zakisupermarket.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExpenseCategory {
    PURCHASES("مشتريات", "Purchases of medicines and supplies"),
    SALARIES("رواتب", "Employee salaries and wages"),
    RENT("إيجار", "Store rent payments"),
    UTILITIES("مرافق", "Electricity, water, internet bills"),
    MAINTENANCE("صيانة", "Equipment and facility maintenance"),
    MARKETING("تسويق", "Advertising and promotional activities"),
    INSURANCE("تأمين", "Insurance premiums"),
    LICENSES("تراخيص", "License and permit fees"),
    TRANSPORT("نقل ومواصلات", "Delivery and transportation costs"),
    OTHER("أخرى", "Other miscellaneous expenses");

    private final String arabicName;
    private final String description;
}