package com.raif.databaseapi.data

import com.raif.databaseapi.domain.PaymentInfo
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentRepository: JpaRepository<PaymentInfo, Long> {
    override fun findAll(): List<PaymentInfo>
    fun findByQrId(qrId: String): PaymentInfo?
}