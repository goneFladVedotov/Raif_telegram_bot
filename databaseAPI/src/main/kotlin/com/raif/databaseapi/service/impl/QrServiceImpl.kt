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
    override fun updateQrInfo(qrId: String, qrStatus: String) {
        val qrInfoToUpdate = qrRepository.findByQrId(qrId)
        qrInfoToUpdate?:throw ResourceNotFoundException("QrInfo not found")
        qrInfoToUpdate.qrStatus = qrStatus
        qrRepository.save(qrInfoToUpdate)
    }
}