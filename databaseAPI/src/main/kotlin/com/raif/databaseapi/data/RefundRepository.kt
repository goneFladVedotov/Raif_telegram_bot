package com.raif.databaseapi.data

import com.raif.databaseapi.domain.RefundInfo
import org.springframework.data.jpa.repository.JpaRepository

interface RefundRepository: JpaRepository<RefundInfo, Long> {
    fun findByRefundId(refundId: String): RefundInfo?
}