package com.kasperovich.desoccer.service.product;

import com.kasperovich.desoccer.exception.NotDeletableStatusException;
import com.kasperovich.desoccer.models.Product;

import java.util.List;

public interface ProductService {

    public List<Product>findAll();

    public Product createProduct(Product product);

    public Product deleteProduct(Long id) throws NotDeletableStatusException;

    public Product updateProduct(Product product);


}
