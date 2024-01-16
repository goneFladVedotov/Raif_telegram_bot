package com.raif.databaseapi.mapper.impl

import com.raif.databaseapi.domain.RefundInfo
import com.raif.databaseapi.mapper.Mapper
import com.raif.databaseapi.web.dto.RefundInfoDto
import org.springframework.stereotype.Service

@Service
class RefundMapper: Mapper<RefundInfo, RefundInfoDto> {
    override fun entityToDto(entity: RefundInfo): RefundInfoDto {
        val dto = RefundInfoDto(
            entity.amount,
            entity.order,
            entity.refundId,
            entity.status,
            entity.paymentDetails,
            entity.transactionId
        )
        return dto
    }

    override fun dtoToEntity(dto: RefundInfoDto): RefundInfo {
        val entity = RefundInfo(
            dto.amount,
            dto.order,
            dto.refundId,
            dto.status,
            dto.paymentDetails,
            dto.transactionId
        )
        return entity
    }
}