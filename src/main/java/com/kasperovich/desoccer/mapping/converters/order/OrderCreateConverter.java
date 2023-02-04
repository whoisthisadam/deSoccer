package com.kasperovich.desoccer.mapping.converters.order;

import com.kasperovich.desoccer.dto.order.OrderCreateDto;
import com.kasperovich.desoccer.mapping.converters.payment.PaymentCreateConverter;
import com.kasperovich.desoccer.models.Edit;
import com.kasperovich.desoccer.models.Order;
import com.kasperovich.desoccer.repository.ProductRepository;
import com.kasperovich.desoccer.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderCreateConverter {

    UserRepository userRepository;

    PaymentCreateConverter paymentCreateConverter;

    ProductRepository productRepository;

    public Order convert(OrderCreateDto orderCreateDto) {
        Order order=new Order();
        order.setUser(userRepository.findById(orderCreateDto.getUserId())
                .orElseThrow(EntityNotFoundException::new));
        order.setTotal(
                orderCreateDto
                        .getProducts()
                        .stream()
                        .map(x->productRepository.findById(x)
                                .orElseThrow(EntityNotFoundException::new).getPrice())
                        .reduce(0L,Long::sum)
        );
        order.setProducts(orderCreateDto.getProducts().stream().map(x->productRepository.findById(x).orElseThrow(EntityNotFoundException::new)).collect(Collectors.toSet()));
        order.setEditData(new Edit(new Timestamp(new Date().getTime()), null));

        order.setPayment(paymentCreateConverter.convert(orderCreateDto.getPayment(), order));
        return order;
    }
}
