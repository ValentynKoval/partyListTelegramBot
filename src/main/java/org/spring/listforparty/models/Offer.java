package org.spring.listforparty.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "offers")
public class Offer {
    @Id
    private String id;

    private String text;
}
