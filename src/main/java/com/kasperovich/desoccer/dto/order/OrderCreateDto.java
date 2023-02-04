package com.kasperovich.desoccer.dto.order;

import com.kasperovich.desoccer.dto.payment.PaymentCreateDto;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

;

@Data
@FieldDefaults(level = PRIVATE, makeFinal = false)
public class OrderCreateDto {

    Long userId;

    PaymentCreateDto payment;

    List<Long>products;

}
