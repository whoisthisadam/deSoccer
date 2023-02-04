package com.kasperovich.desoccer.dto.payment;

import com.kasperovich.desoccer.enums.PaymentProviders;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE, makeFinal = false)
public class PaymentCreateDto {

    PaymentProviders provider;

}
