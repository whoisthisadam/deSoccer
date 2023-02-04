package com.kasperovich.desoccer.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kasperovich.desoccer.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@EqualsAndHashCode(callSuper=false)
@FieldDefaults(level = PRIVATE, makeFinal = false)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ProductCreateDto {

    String name;

    String brand;

    Long price;

    ProductStatus status;

}
