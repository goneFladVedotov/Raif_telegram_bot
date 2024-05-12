package com.raif.botConstructors.services

interface BotobotService {
    fun updateToken(token: String)

    fun updateOrderStatus(id: String, status: String)

    fun paidOrder(id: String)
}