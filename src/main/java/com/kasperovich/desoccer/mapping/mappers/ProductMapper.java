package com.kasperovich.desoccer.mapping.mappers;

import com.kasperovich.desoccer.dto.product.ProductCreateDto;
import com.kasperovich.desoccer.dto.product.ProductGetDto;
import com.kasperovich.desoccer.models.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    public Product toEntity(ProductCreateDto productCreateDto);

    public ProductGetDto toDto(Product product);


}
