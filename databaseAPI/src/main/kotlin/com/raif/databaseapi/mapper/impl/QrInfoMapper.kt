package com.raif.databaseapi.mapper.impl

import com.raif.databaseapi.domain.QrInfo
import com.raif.databaseapi.mapper.Mapper
import com.raif.databaseapi.web.dto.QrInfoDto
import org.springframework.stereotype.Service

@Service
class QrInfoMapper: Mapper<QrInfo, QrInfoDto> {
    override fun entityToDto(entity: QrInfo): QrInfoDto {
        val dto = QrInfoDto(entity.qrId, entity.qrStatus, entity.payload, entity.qrUrl)
        return dto
    }

    override fun dtoToEntity(dto: QrInfoDto): QrInfo {
        val entity = QrInfo(dto.qrId, dto.qrStatus, dto.payload, dto.qrUrl)
        return entity
    }

    override fun entityToDto(entities: List<QrInfo>): List<QrInfoDto> {
        return entities.map { entityToDto(it) }
    }

    override fun dtoToEntity(dtos: List<QrInfoDto>): List<QrInfo> {
        return dtos.map { dtoToEntity(it) }
    }
}