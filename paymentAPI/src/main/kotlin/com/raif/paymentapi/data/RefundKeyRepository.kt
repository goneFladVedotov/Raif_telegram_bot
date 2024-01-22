package com.raif.paymentapi.data

import com.raif.paymentapi.domain.model.RefundKey
import org.springframework.data.jpa.repository.JpaRepository

interface RefundKeyRepository : JpaRepository<RefundKey, Long> {
    override fun findAll(): List<RefundKey>
    fun deleteByRefundId(refundId: String)
}