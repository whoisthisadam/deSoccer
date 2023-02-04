package com.kasperovich.desoccer.mapping.mappers;

import com.kasperovich.desoccer.dto.address.AddressCreateDto;
import com.kasperovich.desoccer.models.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    public Address toEntity(AddressCreateDto dto);

    public AddressCreateDto toDto(Address address);

}
