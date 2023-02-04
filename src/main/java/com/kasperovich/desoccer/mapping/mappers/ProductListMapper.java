package com.kasperovich.desoccer.mapping.mappers;

import com.kasperovich.desoccer.dto.product.ProductCreateDto;
import com.kasperovich.desoccer.dto.product.ProductGetDto;
import com.kasperovich.desoccer.models.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface ProductListMapper {

    public List<Product>toEntityList(List<ProductCreateDto> dtos);

    public List<ProductGetDto>toDto(List<Product>products);

}
