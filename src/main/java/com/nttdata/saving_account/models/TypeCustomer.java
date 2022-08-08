package com.nttdata.saving_account.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeCustomer {
    String id;
    EnumTypeCustomer value;
    public enum EnumTypeCustomer {
        EMPRESARIAL, PERSONAL
    }
}
