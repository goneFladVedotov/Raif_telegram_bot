package com.raif.databaseapi.mapper.impl

import com.raif.databaseapi.domain.QrInfo
import com.raif.databaseapi.domain.SubscriptionInfo
import com.raif.databaseapi.mapper.Mapper
import com.raif.databaseapi.service.QrService
import com.raif.databaseapi.web.dto.SubscriptionDto
import org.springframework.stereotype.Component

@Component
class SubscriptionInfoMapper(
    private val qrService: QrService
) : Mapper<SubscriptionInfo, SubscriptionDto> {
    override fun entityToDto(entity: SubscriptionInfo): SubscriptionDto {
        return SubscriptionDto(
            entity.subscriptionId,
            entity.createDate,
            entity.status,
            entity.qrInfo.qrId,
            entity.qrInfo.payload,
            entity.qrInfo.qrUrl
        )
    }

    override fun entityToDto(entities: List<SubscriptionInfo>): List<SubscriptionDto> {
        return entities.map { entityToDto(it) }
    }

    override fun dtoToEntity(dto: SubscriptionDto): SubscriptionInfo {
        val qrInfo = qrService.saveQrInfo(
            QrInfo(
                dto.qrId,
                "SUBSCRIBED",
                dto.payload,
                dto.url
            )
        )
        return SubscriptionInfo(
            dto.subscriptionId,
            dto.createDate,
            dto.status,
            qrInfo
        )
    }

    override fun dtoToEntity(dtos: List<SubscriptionDto>): List<SubscriptionInfo> {
        return dtos.map { dtoToEntity(it) }
    }
}