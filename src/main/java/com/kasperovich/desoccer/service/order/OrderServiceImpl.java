package com.kasperovich.desoccer.service.order;

import com.kasperovich.desoccer.enums.OrderStatus;
import com.kasperovich.desoccer.enums.PaymentStatus;
import com.kasperovich.desoccer.exception.NotDeletableStatusException;
import com.kasperovich.desoccer.models.Edit;
import com.kasperovich.desoccer.models.Order;
import com.kasperovich.desoccer.repository.OrderRepository;
import com.kasperovich.desoccer.repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;

    PaymentRepository paymentRepository;
    @Override
    public List<Order> findAll() {
        return orderRepository.findAll().stream().filter(x-> !x.getIsDeleted()).collect(Collectors.toList());
    }

    @Override
    public Order createOrder(@Valid Order order) {
        if(order.getPayment().getStatus()== PaymentStatus.NOT_PAID){
            order.setOrderStatus(OrderStatus.NOT_STARTED);
        }
        else order.setOrderStatus(OrderStatus.IN_PROGRESS);
        if(order.getUser().getUserDiscount()!=null){
            order.setTotal(order.getTotal()-(order.getTotal()/100)*order.getUser().getUserDiscount().getDiscountPercent());
            order.getPayment().setAmount(order.getTotal());
        }
        order=orderRepository.save(order);
        paymentRepository.save(order.getPayment());
        return order;
    }

    @Override
    public Order updateOrder(Order order) {
        order.setEditData(new Edit(order.getEditData().getCreationDate(), new Timestamp(new Date().getTime())));
        order.setTotal(order.getTotal()-(order.getTotal()/100)*order.getUser().getUserDiscount().getDiscountPercent());
        order.getPayment().setAmount(order.getTotal());
        order=orderRepository.save(order);
        if(order.getPayment()!=null){
            paymentRepository.save(order.getPayment());
        }
        return order;
    }

    @Override
    public Order deleteOrder(Long id) throws NotDeletableStatusException {
        Order order=orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if(order.getOrderStatus()== OrderStatus.DONE||order.getOrderStatus()== OrderStatus.CANCELLED){
            order.setIsDeleted(true);
            return orderRepository.save(order);
        }
        else throw new NotDeletableStatusException(
                "Unable to delete order with status "+order.getOrderStatus().toString()
        );
    }
}
