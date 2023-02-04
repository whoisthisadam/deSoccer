package com.kasperovich.desoccer.mapping.mappers;

import com.kasperovich.desoccer.dto.payment.PaymentGetDto;
import com.kasperovich.desoccer.models.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    public PaymentGetDto toDto(Payment payment);

}
