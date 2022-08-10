package com.nttdata.saving_account.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
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
    @UniqueElements(message = "exists")
    private Customer customer;
    @NotNull
    private float commission;
    @NotNull
    private int movement_limit;
    private int movementNumber;
    private float amountAvailable;
    private LocalDateTime registration_date;

}
