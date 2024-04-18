package com.raif.databaseapi.service.impl

import com.raif.databaseapi.data.SubscriptionRepository
import com.raif.databaseapi.domain.SubscriptionInfo
import com.raif.databaseapi.exception.ResourceNotFoundException
import com.raif.databaseapi.service.SubscriptionService
import org.springframework.stereotype.Service
import java.lang.IllegalStateException

@Service
class SubscriptionServiceImpl(
    private val subscriptionRepository: SubscriptionRepository
) : SubscriptionService {
    override fun save(subscriptionInfo: SubscriptionInfo) {
        if (subscriptionRepository.findBySubscriptionId(subscriptionInfo.subscriptionId) != null) {
            throw IllegalStateException("subscription info already exists")
        }
        subscriptionRepository.save(subscriptionInfo)
    }

    override fun update(subscriptionId: String, status: String) {
        val subscription = subscriptionRepository.findBySubscriptionId(subscriptionId)
            ?: throw ResourceNotFoundException("subscription not found")
        subscription.status = status;
        subscriptionRepository.saveAndFlush(subscription)
    }

    override fun get(subscriptionId: String): SubscriptionInfo {
        return subscriptionRepository.findBySubscriptionId(subscriptionId)
            ?: throw ResourceNotFoundException("subscription not found")
    }

    override fun getAll(): List<SubscriptionInfo> {
        return subscriptionRepository.findAll()
    }
}