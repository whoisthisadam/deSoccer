package com.kasperovich.mapping.converters.order;

import com.kasperovich.dto.order.OrderUpdateDto;
import com.kasperovich.mapping.converters.payment.PaymentUpdateConverter;
import com.kasperovich.models.Order;
import com.kasperovich.models.Product;
import com.kasperovich.repository.OrderRepository;
import com.kasperovich.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderUpdateConverter implements Converter<OrderUpdateDto, Order> {

    OrderRepository orderRepository;
    ProductRepository productRepository;

    PaymentUpdateConverter paymentUpdateConverter;


    @Override
    public Order convert(OrderUpdateDto orderUpdateDto){
        return  null;
    }

    public Order doConvert(OrderUpdateDto orderUpdateDto, Long id) throws Exception {

        Order order=orderRepository.findById(id).orElseThrow(()->new EntityNotFoundException("No order with this ID"));


        if(orderUpdateDto.getProducts()!=null){
            order.setProducts(orderUpdateDto
                    .getProducts()
                    .stream()
                    .map(x->productRepository.findById(x)
                            .orElseThrow(()->new EntityNotFoundException("No product with this ID")))
                    .collect(Collectors.toSet()));
        }

        order.setTotal(order.getProducts().stream().map(Product::getPrice).reduce(Long::sum).orElseThrow(Exception::new));

        if(orderUpdateDto.getPayment()!=null){
            order.setPayment(paymentUpdateConverter.convert(orderUpdateDto.getPayment(), order.getPayment().getId()));
        }
        return order;

    }
}
