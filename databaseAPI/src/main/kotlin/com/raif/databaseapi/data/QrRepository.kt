package com.raif.databaseapi.data

import com.raif.databaseapi.domain.QrInfo
import org.springframework.data.jpa.repository.JpaRepository

interface QrRepository : JpaRepository<QrInfo, Long> {
    fun findByQrId(qrId: String): QrInfo?
    override fun findAll(): List<QrInfo>
}