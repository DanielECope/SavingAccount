package com.nttdata.saving_account.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "SavingAccount")
public class SavingAccount {
    @Id
    @NotNull
    private String accountNumber;
    @NotNull
    private Customer customer;
    @NotNull
    private float commission;
    @NotNull
    private int movement_limit;
    //@JsonFormat(pattern = "dd::MM::yyyy KK:mm a")
    private LocalDateTime registration_date;

}
