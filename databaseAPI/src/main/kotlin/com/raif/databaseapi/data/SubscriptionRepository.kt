package com.raif.databaseapi.data

import com.raif.databaseapi.domain.SubscriptionInfo
import org.springframework.data.jpa.repository.JpaRepository

interface SubscriptionRepository : JpaRepository<SubscriptionInfo, Long> {
    fun findBySubscriptionId(id: String): SubscriptionInfo?
    override fun findAll(): List<SubscriptionInfo>
}