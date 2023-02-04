package com.kasperovich.desoccer.dto.discount;

import com.kasperovich.desoccer.enums.Discounts;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE, makeFinal = false)
public class DiscountCreateDto {

    Discounts name;

    Integer discountPercent;

    List<Long>userIds;

}
