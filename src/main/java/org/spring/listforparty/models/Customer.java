package org.spring.listforparty.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "customer")
public class Customer {
    @Id
    private String id;

    private long chatId;
    private String firstName;
    private String lastName;
    private String username;

    @DBRef
    private List<Offer> offers;
}
