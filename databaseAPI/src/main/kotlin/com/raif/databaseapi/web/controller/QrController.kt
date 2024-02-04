package com.raif.databaseapi.web.controller

import com.raif.databaseapi.web.dto.QrInfoDto
import org.springframework.http.ResponseEntity

interface QrController {
    fun saveQrInfo(qrInfoDto: QrInfoDto): ResponseEntity<*>
    fun getQrInfo(qrId: String): ResponseEntity<QrInfoDto>
    fun getAll(): ResponseEntity<List<QrInfoDto>>
    fun updateQrStatus(qrId: String, qrStatus: String): ResponseEntity<*>
}