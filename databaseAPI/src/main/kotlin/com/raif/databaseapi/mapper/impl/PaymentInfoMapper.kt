package com.raif.databaseapi.mapper.impl

import com.raif.databaseapi.domain.PaymentInfo
import com.raif.databaseapi.mapper.Mapper
import com.raif.databaseapi.web.dto.PaymentInfoDto
import org.springframework.stereotype.Service

@Service
class PaymentInfoMapper: Mapper<PaymentInfo, PaymentInfoDto> {
    override fun entityToDto(entity: PaymentInfo): PaymentInfoDto {
        val dto = PaymentInfoDto(
            entity.additionalInfo,
            entity.amount,
            entity.createDate,
            entity.currency,
            entity.order,
            entity.paymentStatus,
            entity.qrId,
            entity.sbpMerchantId,
            entity.transactionDate,
            entity.transactionId)
        return dto
    }

    override fun dtoToEntity(dto: PaymentInfoDto): PaymentInfo {
        val entity = PaymentInfo(
            dto.additionalInfo,
            dto.amount,
            dto.createDate,
            dto.currency,
            0,
            dto.order,
            dto.paymentStatus,
            dto.qrId,
            dto.sbpMerchantId,
            dto.transactionDate,
            dto.transactionId,
        )
        return entity
    }
}