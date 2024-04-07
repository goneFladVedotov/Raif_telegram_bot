package com.raif.databaseapi.service

import com.raif.databaseapi.domain.QrInfo

interface QrService {
    fun saveQrInfo(qrInfo: QrInfo): QrInfo
    fun getQrInfo(qrId: String): QrInfo
    fun getAll(): List<QrInfo>
    fun updateQrInfo(qrId: String, qrStatus: String)
}