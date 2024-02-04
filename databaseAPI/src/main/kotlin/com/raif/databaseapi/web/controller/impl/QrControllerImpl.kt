package com.raif.databaseapi.web.controller.impl

import com.raif.databaseapi.domain.QrInfo
import com.raif.databaseapi.mapper.Mapper
import com.raif.databaseapi.service.QrService
import com.raif.databaseapi.web.controller.QrController
import com.raif.databaseapi.web.dto.QrInfoDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("database-api/v1/qrs")
class QrControllerImpl(
    private val qrService: QrService,
    private val mapper: Mapper<QrInfo, QrInfoDto>
): QrController {
    @PostMapping
    override fun saveQrInfo(@RequestBody qrInfoDto: QrInfoDto): ResponseEntity<*> {
        val qrInfo = mapper.dtoToEntity(qrInfoDto)
        qrService.saveQrInfo(qrInfo)
        return ResponseEntity.ok(null)
    }

    @GetMapping("/{qrId}")
    override fun getQrInfo(@PathVariable("qrId") qrId: String): ResponseEntity<QrInfoDto> {
        val qrInfoDto = mapper.entityToDto(qrService.getQrInfo(qrId))
        return ResponseEntity.ok(qrInfoDto)
    }

    @GetMapping
    override fun getAll(): ResponseEntity<List<QrInfoDto>> {
        return ResponseEntity.ok(mapper.entityToDto(qrService.getAll()))
    }

    @PutMapping("/{qrId}")
    override fun updateQrStatus(@PathVariable("qrId") qrId: String, @RequestBody qrStatus: String): ResponseEntity<*> {
        qrService.updateQrInfo(qrId, qrStatus)
        return ResponseEntity.ok(null)
    }
}