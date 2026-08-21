package com.jcooldevelopment.easybank_api.dto.Account;

import java.util.List;
import java.util.UUID;

import com.jcooldevelopment.easybank_api.annotations.EnumValidatorAnnotation;
import com.jcooldevelopment.easybank_api.contracts.enums.AccountPurpose;
import com.jcooldevelopment.easybank_api.contracts.enums.AccountStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountAdminDto {

    @NotNull(message="There is no branch selected.")
    private Long branchId;

    @EnumValidatorAnnotation(enumClass = AccountStatus.class, message = "The account status value is not valid.")
    private String status;

    @EnumValidatorAnnotation(enumClass = AccountPurpose.class, message = "The purpose value is not valid.")
    private String accountPurpose;

    @NotNull(message="There is no account type selected.")
    private UUID accountTypeId;

    private List<UUID> userIds;
}
