package com.nttdata.saving_account.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Customer")
public class Customer {
    @Id
    String dni;
    String name;
    String lastName;
    TypeCustomer typeCustomer;
}
