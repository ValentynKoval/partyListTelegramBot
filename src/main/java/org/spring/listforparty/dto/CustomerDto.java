package org.spring.listforparty.dto;

import lombok.Data;
import org.spring.listforparty.models.Offer;

@Data
public class CustomerDto {
    private long chatId;
    private String firstName;
    private String lastName;
    private String username;
    private Offer offer;
}
