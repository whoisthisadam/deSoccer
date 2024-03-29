package com.kasperovich.desoccer.controller;

import com.kasperovich.desoccer.dto.order.OrderCreateDto;
import com.kasperovich.desoccer.dto.order.OrderGetDto;
import com.kasperovich.desoccer.dto.order.OrderUpdateDto;
import com.kasperovich.desoccer.exception.NotDeletableStatusException;
import com.kasperovich.desoccer.mapping.converters.order.OrderCreateConverter;
import com.kasperovich.desoccer.mapping.converters.order.OrderGetConverter;
import com.kasperovich.desoccer.mapping.converters.order.OrderUpdateConverter;
import com.kasperovich.desoccer.models.Order;
import com.kasperovich.desoccer.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Validated
@Slf4j
@RequestMapping("data/orders")
@RequiredArgsConstructor
@Tag(name = "Orders")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderService orderService;

    OrderCreateConverter orderCreateConverter;

    OrderGetConverter orderGetConverter;

    OrderUpdateConverter orderUpdateConverter;


    @Operation(
            summary = "Create order",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Created",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = OrderGetDto.class)))
                            })
            }
    )
    @PostMapping
    @Transactional
    public ResponseEntity<Map<String, OrderGetDto>> create(@RequestBody OrderCreateDto orderCreateDto) {
        Order order = orderCreateConverter.convert(orderCreateDto);
        orderService.createOrder(order);
        return new ResponseEntity<>(Collections.singletonMap("New order:", orderGetConverter.convert(order)), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Find all orders(Admin only)",
            responses = {
                    @ApiResponse(
                          responseCode = "200",
                            description = "Found",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = OrderGetDto.class)))
                            })
            }
            , parameters = {
            @Parameter(
                    in = ParameterIn.HEADER,
                    name = "X-Auth-Token",
                    required = true,
                    description = "JWT Token, can be generated in auth controller /auth")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<OrderGetDto>> findAll() {
        List<OrderGetDto> list = orderService.findAll().stream().map(orderGetConverter::convert).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Update order(Admin&Moderator only)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Updated",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = OrderGetDto.class)))
                            })
            }
            , parameters = {
            @Parameter(
                    in = ParameterIn.HEADER,
                    name = "X-Auth-Token",
                    required = true,
                    description = "JWT Token, can be generated in auth controller /auth")
    })
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PatchMapping("/update")
    public ResponseEntity<Map<String, OrderGetDto>> updateOrder(@RequestBody OrderUpdateDto orderUpdateDto,
                                                                @RequestParam String id) throws Exception {
        Order order = orderUpdateConverter.doConvert(orderUpdateDto, Long.parseLong(id));
        orderService.updateOrder(order);
        return ResponseEntity.ok(Collections.singletonMap("Updated order:", orderGetConverter.convert(order)));
    }

    @Operation(
            summary = "Delete order(Admin only)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Updated",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = String.class)))
                            })
            }
            , parameters = {
            @Parameter(
                    in = ParameterIn.HEADER,
                    name = "X-Auth-Token",
                    required = true,
                    description = "JWT Token, can be generated in auth controller /auth")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/delete")
    public ResponseEntity<String> deleteOrder(@RequestParam String Id) throws NotDeletableStatusException {
        orderService.deleteOrder(Long.parseLong(Id));
        return ResponseEntity.ok("Order with ID " + Id + " deleted");
    }


}
