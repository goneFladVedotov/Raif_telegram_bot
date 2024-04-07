package com.raif.databaseapi.service.impl

import com.raif.databaseapi.data.PaymentRepository
import com.raif.databaseapi.domain.PaymentInfo
import com.raif.databaseapi.exception.ResourceNotFoundException
import com.raif.databaseapi.service.PaymentService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PaymentServiceImpl(
    private val paymentRepository: PaymentRepository
): PaymentService {

    @Transactional
    override fun savePaymentInfo(paymentInfo: PaymentInfo) {
        if (paymentRepository.findByQrId(paymentInfo.qrId) != null) {
            throw IllegalStateException("payment info already exists")
        }
        paymentRepository.save(paymentInfo)
    }

    @Transactional
    override fun updatePaymentInfo(qrId: String, paymentStatus: String) {
        val paymentInfoToUpdate = paymentRepository.findByQrId(qrId)?:
        throw ResourceNotFoundException("payment info not found")
        paymentInfoToUpdate.paymentStatus = paymentStatus
        paymentRepository.saveAndFlush(paymentInfoToUpdate)
    }

    override fun getPaymentInfo(qrId: String): PaymentInfo {
        return paymentRepository.findByQrId(qrId)?:
        throw ResourceNotFoundException("payment info not found")
    }

    override fun getAll(): List<PaymentInfo> {
        return paymentRepository.findAll()
    }
}