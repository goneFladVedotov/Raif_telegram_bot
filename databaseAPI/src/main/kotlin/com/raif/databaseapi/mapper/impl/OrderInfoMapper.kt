package com.raif.databaseapi.mapper.impl

import com.raif.databaseapi.domain.OrderInfo
import com.raif.databaseapi.mapper.Mapper
import com.raif.databaseapi.web.dto.OrderInfoDto
import org.springframework.stereotype.Component

@Component
class OrderInfoMapper : Mapper<OrderInfo, OrderInfoDto> {
    override fun entityToDto(entity: OrderInfo): OrderInfoDto {
        return OrderInfoDto(
            entity.orderId,
            entity.amount,
            entity.comment,
            entity.status,
            entity.createDate,
            entity.expirationDate,
            entity.orderId
        )
    }

    override fun entityToDto(entities: List<OrderInfo>): List<OrderInfoDto> {
        return entities.map { entityToDto(it) }
    }

    override fun dtoToEntity(dto: OrderInfoDto): OrderInfo {
        return OrderInfo(
            dto.orderId,
            dto.amount,
            dto.comment,
            dto.status,
            dto.createDate,
            dto.expirationDate,
            dto.orderId
        )
    }

    override fun dtoToEntity(dtos: List<OrderInfoDto>): List<OrderInfo> {
        return dtos.map { dtoToEntity(it) }
    }
}