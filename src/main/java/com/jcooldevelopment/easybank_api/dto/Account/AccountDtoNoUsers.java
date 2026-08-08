package com.jcooldevelopment.easybank_api.dto.Account;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDtoNoUsers {

    private UUID id;

    private String iban;

    private String bicSwift;

    private String place;
}
