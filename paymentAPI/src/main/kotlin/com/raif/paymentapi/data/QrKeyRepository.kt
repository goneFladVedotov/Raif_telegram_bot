package com.raif.paymentapi.data

import com.raif.paymentapi.domain.model.QrKey
import org.springframework.data.jpa.repository.JpaRepository

interface QrKeyRepository: JpaRepository<QrKey, Long> {
    override fun findAll(): MutableList<QrKey>
    fun deleteByQrId(qrId: String)
}