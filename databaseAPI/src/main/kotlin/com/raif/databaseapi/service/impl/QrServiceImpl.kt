package com.raif.databaseapi.service.impl

import com.raif.databaseapi.data.QrRepository
import com.raif.databaseapi.domain.QrInfo
import com.raif.databaseapi.exception.ResourceNotFoundException
import com.raif.databaseapi.service.QrService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QrServiceImpl (
    private val qrRepository: QrRepository
): QrService {
    override fun saveQrInfo(qrInfo: QrInfo) {
        qrRepository.save(qrInfo)
    }

    override fun getQrInfo(qrId: String): QrInfo {
        val qrInfo = qrRepository.findByQrId(qrId)
        return qrInfo?:throw ResourceNotFoundException("QrInfo not found")
    }

    @Transactional
    override fun updateQrInfo(qrInfo: QrInfo) {
        val qrInfoToUpdate = qrRepository.findByQrId(qrInfo.qrId)
        qrInfoToUpdate?:throw ResourceNotFoundException("QrInfo not found")
        qrInfoToUpdate.qrStatus = qrInfo.qrStatus
        qrInfoToUpdate.payload = qrInfo.payload
        qrInfoToUpdate.qrUrl = qrInfo.qrUrl
        qrRepository.save(qrInfoToUpdate)
    }
}