package com.raif.databaseapi.data

import com.raif.databaseapi.domain.PaymentInfo
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentRepository: JpaRepository<PaymentInfo, Long> {
    fun findByQrId(qrId: String): PaymentInfo?
}