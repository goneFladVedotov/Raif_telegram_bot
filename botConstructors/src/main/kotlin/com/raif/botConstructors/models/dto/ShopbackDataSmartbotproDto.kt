package com.raif.botConstructors.models.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ShopbackDataSmartbotproDto(
    @JsonProperty("total")
    val total: Double,
    @JsonProperty("text")
    val text: String,
    @JsonProperty("order_id")
    val order_id: String,
    @JsonProperty("products")
    val products: List<ProductSmartbotproDto>
)