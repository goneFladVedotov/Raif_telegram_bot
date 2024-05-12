package com.raif.botConstructors.models.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ProductSmartbotproDto(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("price")
    val price: Double,
    @JsonProperty("external_id")
    val external_id: String,
    @JsonProperty("count")
    val count: Double,
    @JsonProperty("modifications")
    val modifications: List<Any>
)
