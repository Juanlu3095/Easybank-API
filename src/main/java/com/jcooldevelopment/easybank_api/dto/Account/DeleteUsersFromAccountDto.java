package com.jcooldevelopment.easybank_api.dto.Account;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUsersFromAccountDto {

    @NotEmpty(message = "The ids list must not be empty.")
    private List<UUID> ids;
}
