package com.archproj.erp_backend.mappers;

import com.archproj.erp_backend.entities.OrderEntity;
import com.archproj.erp_backend.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toModel(OrderEntity entity);

    @Mapping(target = "id", ignore = true) // Usually ID is ignored during creation from model if auto-generated, or
                                           // mapped if update
    OrderEntity toEntity(Order model);
}
