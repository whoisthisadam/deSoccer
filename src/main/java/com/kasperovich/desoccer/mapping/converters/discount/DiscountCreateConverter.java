package com.kasperovich.desoccer.mapping.converters.discount;

import com.kasperovich.desoccer.dto.discount.DiscountCreateDto;
import com.kasperovich.desoccer.models.Discount;
import com.kasperovich.desoccer.models.Edit;
import com.kasperovich.desoccer.models.User;
import com.kasperovich.desoccer.repository.ProductRepository;
import com.kasperovich.desoccer.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiscountCreateConverter implements Converter<DiscountCreateDto, Discount> {

    UserRepository userRepository;

    ProductRepository productRepository;

    @Override
    public Discount convert(DiscountCreateDto discountCreateDto) {
        Discount discount=new Discount();
        discount.setName(discountCreateDto.getName());
        discount.setDiscountPercent(discountCreateDto.getDiscountPercent());
        discount.setEditData(new Edit(new Timestamp(new Date().getTime()), null));
        if(discountCreateDto.getUserIds()!=null){
            Set<User> users=new HashSet<>(userRepository.findAllById(discountCreateDto.getUserIds()));
            discount.setUsers(users);
            users.forEach(x->x.setUserDiscount(discount));
        }
        return discount;
    }
}
