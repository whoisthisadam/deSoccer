package com.kasperovich.desoccer.dto.address;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressCreateDto {

    String lineOne;

    String lineTwo;

    String lineThree;

    String city;

    String province;

    String postCode;

    String country;

}
