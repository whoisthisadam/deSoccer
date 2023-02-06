package com.kasperovich.desoccer.controller;

import com.kasperovich.desoccer.dto.discount.DiscountCreateDto;
import com.kasperovich.desoccer.dto.discount.DiscountGetDto;
import com.kasperovich.desoccer.dto.order.OrderGetDto;
import com.kasperovich.desoccer.mapping.converters.discount.DiscountCreateConverter;
import com.kasperovich.desoccer.mapping.converters.discount.DiscountGetConverter;
import com.kasperovich.desoccer.models.Discount;
import com.kasperovich.desoccer.service.discount.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/data/discounts")
@RequiredArgsConstructor
@Tag(name = "Discounts")
public class DiscountController {

    private final DiscountService discountService;

    private final DiscountCreateConverter discountCreateConverter;

    private final DiscountGetConverter discountGetConverter;

    @Operation(
            summary = "Create discount(Admin&Moderator only)",
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
    @Secured({"ROLE_ADMIN","ROLE_MODERATOR"})
    @Transactional
    @PostMapping
    public ResponseEntity<Map<String, DiscountGetDto>>createDiscount(@RequestBody DiscountCreateDto discountCreateDto){
        Discount discount=discountCreateConverter.convert(discountCreateDto);
        discountService.createDiscount(discount);
        return new ResponseEntity<>(Collections.singletonMap("Created discount:", discountGetConverter.convert(discount)), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Find all discounts(Admin&Moderator only)",
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
    @Secured({"ROLE_ADMIN","ROLE_MODERATOR"})
    @GetMapping
    public ResponseEntity<List<DiscountGetDto>>findAllDiscount(){
        List<DiscountGetDto>list=discountService.findAll()
                .stream().map(discountGetConverter::convert).collect(Collectors.toList());
        return  ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Delete discount(Admin only)",
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
    @PatchMapping
    public ResponseEntity<String>deleteDiscount(@RequestParam String id){
        discountService.deleteDiscount(Long.parseLong(id));
        return ResponseEntity.ok("Discount with id "+ id+" deleted");
    }




}
