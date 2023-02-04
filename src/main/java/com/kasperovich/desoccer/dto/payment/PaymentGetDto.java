package com.kasperovich.desoccer.dto.payment;


import com.kasperovich.desoccer.enums.PaymentProviders;
import com.kasperovich.desoccer.enums.PaymentStatus;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE, makeFinal = false)
public class PaymentGetDto {

    Long id;

    Long amount;

    PaymentProviders provider;

    PaymentStatus status;

}
