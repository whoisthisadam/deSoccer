package com.kasperovich.desoccer.mapping.converters.product;

import com.kasperovich.desoccer.dto.product.ProductCreateDto;
import com.kasperovich.desoccer.models.Product;
import com.kasperovich.desoccer.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductUpdateConverter implements Converter<ProductCreateDto, Product> {

    ProductRepository productRepository;


    @Override
    public Product convert(ProductCreateDto productCreateDto) {
        return null;
    }

    public Product doConvert(ProductCreateDto productCreateDto, Long id) throws EntityNotFoundException {
        Product product=productRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Product with this id does not exist"));
        product.setId(id);

        product.setName(
                Optional.ofNullable(productCreateDto.getName()).orElse(product.getName())
        );
        product.setBrand(
                Optional.ofNullable(
                                productCreateDto.getBrand()).orElse(product.getBrand())
        );
        product.setPrice(
                Optional.ofNullable(productCreateDto.getPrice()).orElse(product.getPrice())
        );
        product.setStatus(
                Optional.ofNullable(
                                productCreateDto.getStatus())
                        .orElse(product.getStatus())
        );
        return product;
    }
}
