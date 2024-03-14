package org.spring.listforparty.services;

import lombok.RequiredArgsConstructor;
import org.spring.listforparty.dto.CustomerDto;
import org.spring.listforparty.models.Customer;
import org.spring.listforparty.repo.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public void saveNewCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setUsername(customerDto.getUsername());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setChatId(customerDto.getChatId());
        customer.setOffers(List.of(customerDto.getOffer()));
        customerRepository.save(customer);
    }
}
