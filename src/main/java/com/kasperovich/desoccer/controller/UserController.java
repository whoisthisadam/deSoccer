package com.kasperovich.desoccer.controller;

import com.kasperovich.desoccer.dto.users.UserCreateDto;
import com.kasperovich.desoccer.dto.users.UserGetDto;
import com.kasperovich.desoccer.mapping.converters.discount.DiscountGetConverter;
import com.kasperovich.desoccer.mapping.converters.user.UserUpdateConverter;
import com.kasperovich.desoccer.mapping.mappers.UserListMapper;
import com.kasperovich.desoccer.mapping.mappers.UserMapper;
import com.kasperovich.desoccer.models.User;
import com.kasperovich.desoccer.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
@RequestMapping("data/users")
@RequiredArgsConstructor
@Tag(name = "Users")
@CacheConfig(cacheNames = "data")
public class UserController {

    private final UserListMapper userListMapper;

    private final UserService userService;

    private final UserMapper userMapper;

    private final UserUpdateConverter userUpdateConverter;

    private final DiscountGetConverter discountGetConverter;

    @Operation(
    summary = "Gets all users(Admin only)",
    responses = {
        @ApiResponse(
                responseCode = "200",
                description = "Found the users",
                content =
                @Content(
                        mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = UserGetDto.class))))
    },parameters = {
        @Parameter(
                in = ParameterIn.HEADER,
                name = "X-Auth-Token",
                required = true,
                description = "JWT Token, can be generated in auth controller /auth")
    })
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping
    @Cacheable
    public ResponseEntity<List<UserGetDto>>findAll(){
        List<User>users=userService.findAll();
        List<UserGetDto>userGetDtos=users
                .stream()
                .map(
                        x->{
                            UserGetDto userGetDto=userMapper.toDto(x);
                            if(x.getUserDiscount()!=null){
                                userGetDto.setDiscount(discountGetConverter.convert(x.getUserDiscount()));
                            }
                            return userGetDto;
                        }
                ).collect(Collectors.toList());
        return ResponseEntity.ok(userGetDtos);
    }

    @Operation(
            summary = "Update user(Admin only)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User updated",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserGetDto.class))))
            },parameters = {
            @Parameter(
                    in = ParameterIn.HEADER,
                    name = "X-Auth-Token",
                    required = true,
                    description = "JWT Token, can be generated in auth controller /auth")
    })
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @Transactional
    @CachePut
    @PatchMapping("/update")
    public ResponseEntity<Map<String, UserGetDto>>updateUser(@RequestParam String id, @RequestBody UserCreateDto userCreateDto){
        Long Id=Long.parseLong(id);
        User user= userUpdateConverter.doConvert(userCreateDto, Id);
        return new ResponseEntity<>(
                Collections.singletonMap(
                        "Updated user:", userMapper.toDto(userService.updateUser(user))), HttpStatus.OK
        );
    }

    @Operation(
            summary = "Delete user(Admin only)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User updated",
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
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @CacheEvict
    @PatchMapping("/delete")
    public ResponseEntity<String>deleteUser(@RequestParam String id){
        Long iD=Long.parseLong(id);
        userService.deleteUser(iD);
        return ResponseEntity.ok("Deleted user with ID number "+id);
    }
}
