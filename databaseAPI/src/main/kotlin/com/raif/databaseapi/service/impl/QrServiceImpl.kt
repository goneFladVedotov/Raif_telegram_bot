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
    @Transactional
    override fun saveQrInfo(qrInfo: QrInfo) {
        qrRepository.findByQrId(qrInfo.qrId)?:throw ResourceNotFoundException("QrInfo not found")
        qrRepository.save(qrInfo)
    }

    override fun getQrInfo(qrId: String): QrInfo {
        val qrInfo = qrRepository.findByQrId(qrId)
        return qrInfo?:throw ResourceNotFoundException("QrInfo not found")
    }

    override fun getAll(): List<QrInfo> {
        return qrRepository.findAll()
    }

    @Transactional
    override fun updateQrInfo(qrId: String, qrStatus: String) {
        val qrInfoToUpdate = qrRepository.findByQrId(qrId)
        qrInfoToUpdate?:throw ResourceNotFoundException("QrInfo not found")
        qrInfoToUpdate.qrStatus = qrStatus
        qrRepository.saveAndFlush(qrInfoToUpdate)
    }
}