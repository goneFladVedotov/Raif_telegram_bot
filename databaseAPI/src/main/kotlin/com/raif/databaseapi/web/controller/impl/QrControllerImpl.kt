package com.raif.databaseapi.web.controller.impl

import com.raif.databaseapi.web.controller.QrController
import com.raif.databaseapi.domain.QrInfo
import com.raif.databaseapi.mapper.Mapper
import com.raif.databaseapi.service.QrService
import com.raif.databaseapi.web.dto.QrInfoDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
    override fun getQrInfo(@PathVariable("qrId") qrId: String): ResponseEntity<*> {
        val qrInfoDto = mapper.entityToDto(qrService.getQrInfo(qrId))
        return ResponseEntity.ok(qrInfoDto)
    }

    @GetMapping
    override fun getAll(): ResponseEntity<*> {
        return ResponseEntity.ok(qrService.getAll())
    }

    @PutMapping("/{qrId}")
    override fun updateQrStatus(@PathVariable("qrId") qrId: String, @RequestBody qrStatus: String): ResponseEntity<*> {
        qrService.updateQrInfo(qrId, qrStatus)
        return ResponseEntity.ok(null)
    }
}