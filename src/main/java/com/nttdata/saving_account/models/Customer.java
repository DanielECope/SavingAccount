package com.nttdata.saving_account.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Customer")
public class Customer {
    @Id
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @Valid
    private TypeCustomer typeCustomer;
    @NotNull
    @Indexed(unique = true)
    private String document;
}
