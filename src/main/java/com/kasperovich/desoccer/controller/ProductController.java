package com.kasperovich.desoccer.controller;

import com.kasperovich.desoccer.dto.product.ProductCreateDto;
import com.kasperovich.desoccer.dto.product.ProductGetDto;
import com.kasperovich.desoccer.exception.NotDeletableStatusException;
import com.kasperovich.desoccer.mapping.converters.product.ProductUpdateConverter;
import com.kasperovich.desoccer.mapping.mappers.ProductListMapper;
import com.kasperovich.desoccer.mapping.mappers.ProductMapper;
import com.kasperovich.desoccer.models.Product;
import com.kasperovich.desoccer.service.product.ProductService;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@Slf4j
@RequestMapping("data/products")
@RequiredArgsConstructor
@Tag(name = "Products")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductService productService;

    ProductMapper productMapper;

    ProductListMapper productListMapper;

    ProductUpdateConverter productUpdateConverter;

    @Operation(
            summary = "Gets all products",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found products",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProductGetDto.class))))
            })
    @GetMapping
    public ResponseEntity<List<ProductGetDto>>findAll(){
        List<ProductGetDto>dtos=new ArrayList<>(productListMapper.toDto(productService.findAll()));
        return ResponseEntity.ok(dtos);
    }

    @Operation(
            summary = "Create product(Admin&Moderator only)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Created",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ProductGetDto.class)))
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
    public ResponseEntity<Map<String, ProductGetDto> >createProduct(@RequestBody ProductCreateDto productCreateDto){
        Product product=productService.createProduct(productMapper.toEntity(productCreateDto));
        return new ResponseEntity<>(
                Collections.singletonMap("New product:", productMapper.toDto(product)),
                HttpStatus.CREATED
        );
    }


    @Operation(
            summary = "Update product(Admin&Moderator only)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product updated",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProductGetDto.class))))
            },parameters = {
            @Parameter(
                    in = ParameterIn.HEADER,
                    name = "X-Auth-Token",
                    required = true,
                    description = "JWT Token, can be generated in auth controller /auth")
    })
    @Secured({"ROLE_ADMIN","ROLE_MODERATOR"})
    @Transactional
    @PatchMapping("/update")
    public ResponseEntity<Map<String, ProductGetDto>>updateProduct(@RequestParam String id, @RequestBody ProductCreateDto productCreateDto){
        Long iD=Long.parseLong(id);
        Product product=productUpdateConverter.doConvert(productCreateDto, iD);
        productService.updateProduct(product);
        return ResponseEntity.ok(Collections.singletonMap("Updated product:",productMapper.toDto(product)));
    }


    @Operation(
            summary = "Delete product(Admin only)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product deleted",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation =String.class))))
            } ,parameters = {
            @Parameter(
                    in = ParameterIn.HEADER,
                    name = "X-Auth-Token",
                    required = true,
                    description = "JWT Token, can be generated in auth controller /auth")
    })
    @Secured("ROLE_ADMIN")
    @PatchMapping("/delete")
    public ResponseEntity<String>deleteProduct(@RequestParam(value = "ID") String idStr) throws NotDeletableStatusException {
        Long id=Long.parseLong(idStr);
        productService.deleteProduct(id);
        return ResponseEntity.ok("Deleted product with id:"+id);
    }






}
