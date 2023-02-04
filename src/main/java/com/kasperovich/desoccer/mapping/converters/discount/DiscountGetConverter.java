package com.kasperovich.desoccer.mapping.converters.discount;

import com.kasperovich.desoccer.dto.discount.DiscountGetDto;
import com.kasperovich.desoccer.mapping.mappers.ProductListMapper;
import com.kasperovich.desoccer.mapping.mappers.UserListMapper;
import com.kasperovich.desoccer.models.Discount;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiscountGetConverter implements Converter<Discount, DiscountGetDto> {

    UserListMapper userListMapper;

    ProductListMapper productListMapper;

    @Override
    public DiscountGetDto convert(Discount discount) {
        DiscountGetDto discountGetDto=new DiscountGetDto();
        discountGetDto.setId(discount.getId());
        discountGetDto.setName(discount.getName());
        discountGetDto.setPercent(discount.getDiscountPercent());
        return discountGetDto;
    }


}
