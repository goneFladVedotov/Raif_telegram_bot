package com.raif.botConstructors.models.dto

import jakarta.validation.constraints.Email

data class BotobotOrderDto(
    val id: Int,
    val user_chat_id: String,
    val cost_total: Float,
    val cost_cart: Float,
    val cost_discount: Float,
    val cost_delivery: Float,
    val delivery: String,
    val time: String,
    val recipient: String,
    val address: String,
    val mobile: String?,
    val email: String?,
    val goods: List<GoodDto>
) {
    data class GoodDto(
        val id: Int,
        val article: String,
        val title: String,
        val count: Int,
        val price: Float,
        val discount: Float,
        val total: Float
    )
}
