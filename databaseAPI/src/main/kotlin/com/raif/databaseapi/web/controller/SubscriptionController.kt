package com.raif.databaseapi.web.controller

import com.raif.databaseapi.web.dto.SubscriptionDto
import org.springframework.http.ResponseEntity

interface SubscriptionController {
    fun save(subscriptionDto: SubscriptionDto): ResponseEntity<*>
    fun update(subscriptionId: String, status: String): ResponseEntity<*>
    fun get(subscriptionId: String): ResponseEntity<SubscriptionDto>
    fun getAll(): ResponseEntity<List<SubscriptionDto>>
}