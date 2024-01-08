package com.raif.paymentapi.service.impl

import com.raif.paymentapi.data.QrKeyRepository
import com.raif.paymentapi.domain.dto.SbpClientDto
import com.raif.paymentapi.service.DatabaseApiClient
import com.raif.paymentapi.service.QrService
import com.raif.paymentapi.service.SchedulerService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class QrSchedulerService(
    private val qrKeyRepository: QrKeyRepository,
    private val qrService: QrService
) : SchedulerService {
    private val databaseApiClient: DatabaseApiClient = QrDatabaseApiClient()

    @Scheduled(fixedDelay = 5000)
    override fun updateInfo() {
        val qrKeys = qrKeyRepository.findAll()
        if (qrKeys.isEmpty())
            return
        val qrInfoList = qrKeys.map { qrService.getQrInfo(it.qrId, SbpClientDto(it.merchantId, it.secretKey)) }
        for (info in qrInfoList) {
            if (info.qrStatus != "NEW" && info.qrStatus != "IN_PROGRESS") {
                qrKeyRepository.deleteByQrId(info.qrId)
            }
            databaseApiClient.update(info)
        }
    }
}