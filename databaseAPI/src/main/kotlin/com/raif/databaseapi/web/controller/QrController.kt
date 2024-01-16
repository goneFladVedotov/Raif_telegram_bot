package com.raif.databaseapi.web.controller

import com.raif.databaseapi.domain.QrInfo
import com.raif.databaseapi.web.dto.QrInfoDto
import org.springframework.http.ResponseEntity

interface QrController {
    fun saveQrInfo(qrInfoDto: QrInfoDto): ResponseEntity<*>
    fun getQrInfo(qrId: String): ResponseEntity<*>
    fun getAll(): ResponseEntity<*>
    fun updateQrStatus(qrId: String, qrStatus: String): ResponseEntity<*>
}