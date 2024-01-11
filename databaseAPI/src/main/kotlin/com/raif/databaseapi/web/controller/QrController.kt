package com.raif.databaseapi.web.controller

import com.raif.databaseapi.domain.QrInfo
import org.springframework.http.ResponseEntity

interface QrController {
    fun saveQrInfo(qrInfo: QrInfo): ResponseEntity<*>
    fun getQrInfo(qrId: String): ResponseEntity<*>
    fun updateQrStatus(qrId: String, qrStatus: String): ResponseEntity<*>
}