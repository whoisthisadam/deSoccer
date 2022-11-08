package com.kasperovich.controller;

import com.kasperovich.dto.order.OrderCreateDto;
import com.kasperovich.dto.order.OrderGetDto;
import com.kasperovich.dto.order.OrderUpdateDto;
import com.kasperovich.dto.product.ProductGetDto;
import com.kasperovich.dto.roles.RoleGetDto;
import com.kasperovich.mapping.converters.order.OrderCreateConverter;
import com.kasperovich.mapping.converters.order.OrderGetConverter;
import com.kasperovich.mapping.converters.order.OrderUpdateConverter;
import com.kasperovich.models.Order;
import com.kasperovich.service.order.OrderService;
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
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
    public ResponseEntity<Map<String, OrderGetDto>>create(@RequestBody OrderCreateDto orderCreateDto){
        Order order=orderCreateConverter.convert(orderCreateDto);
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
            ,parameters = {
            @Parameter(
                    in = ParameterIn.HEADER,
                    name = "X-Auth-Token",
                    required = true,
                    description = "JWT Token, can be generated in auth controller /auth")
    })
    @Secured("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<List<OrderGetDto>>findAll(){
        List<OrderGetDto>list=orderService.findAll().stream().map(orderGetConverter::convert).collect(Collectors.toList());
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
//            ,parameters = {
//            @Parameter(
//                    in = ParameterIn.HEADER,
//                    name = "X-Auth-Token",
//                    required = true,
//                    description = "JWT Token, can be generated in auth controller /auth")
//    })
    )
//    @Secured({"ROLE_ADMIN","ROLE_MODERATOR"})
    @PatchMapping("/update")
    public ResponseEntity<Map<String, OrderGetDto>>updateOrder(@RequestBody OrderUpdateDto orderUpdateDto,
                                                               @RequestParam String id) throws Exception {
        Order order=orderUpdateConverter.doConvert(orderUpdateDto, Long.parseLong(id));
        orderService.updateOrder(order);
        return ResponseEntity.ok(Collections.singletonMap("Updated order:", orderGetConverter.convert(order)));
    }


}
