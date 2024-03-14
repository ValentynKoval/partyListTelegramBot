package org.spring.listforparty.services;

import lombok.RequiredArgsConstructor;
import org.spring.listforparty.models.Product;
import org.spring.listforparty.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void saveNewProducts(String products) {
        List<String> productsListString = new ArrayList<>(Arrays.asList(products.split("\n")));
        productsListString.remove(0);
        List<Product> productList = new ArrayList<>();
        for (String productString : productsListString) {
            productList.add(new Product(productString));
        }
        productRepository.saveAll(productList);
    }

    public String findAll() {
        List<Product> productList = productRepository.findAll();
        String productString = "";
        for (Product product : productList) {
            productString += product.getProduct() + "\n";
        }
        return productString;
    }
}
