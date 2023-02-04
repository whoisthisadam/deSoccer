package com.kasperovich.desoccer.dto.discount;

import com.kasperovich.desoccer.enums.Discounts;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE, makeFinal = false)
public class DiscountGetDto {

    Long id;

    Discounts name;

    Integer percent;

}
