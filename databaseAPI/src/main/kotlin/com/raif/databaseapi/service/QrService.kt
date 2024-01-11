package com.raif.databaseapi.service

import com.raif.databaseapi.domain.QrInfo

interface QrService {
    fun saveQrInfo(qrInfo: QrInfo)
    fun getQrInfo(qrId: String): QrInfo
    fun updateQrInfo(qrId: String, qrStatus: String)
}