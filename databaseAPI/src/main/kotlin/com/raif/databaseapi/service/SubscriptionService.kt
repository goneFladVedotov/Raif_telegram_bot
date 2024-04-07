package com.raif.databaseapi.service

import com.raif.databaseapi.domain.SubscriptionInfo

interface SubscriptionService {
    fun save(subscriptionInfo: SubscriptionInfo)
    fun update(subscriptionId: String, status: String)
    fun get(subscriptionId: String): SubscriptionInfo
    fun getAll(): List<SubscriptionInfo>
}