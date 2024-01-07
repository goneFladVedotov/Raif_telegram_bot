package com.raif.databaseapi.controller

import com.raif.databaseapi.domain.QrInfo
import org.springframework.http.ResponseEntity

interface QrController {
    fun saveQrInfo(qrInfo: QrInfo): ResponseEntity<*>
    fun getQrInfo(qrId: String): ResponseEntity<*>
    fun updateQrInfo(qrInfo: QrInfo): ResponseEntity<*>
}