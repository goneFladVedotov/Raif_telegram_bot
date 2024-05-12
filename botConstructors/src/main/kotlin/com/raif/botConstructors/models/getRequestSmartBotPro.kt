package com.raif.botConstructors.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.raif.botConstructors.models.dto.ShopbackDataSmartbotproDto

data class getRequestSmartBotPro(
    @JsonProperty("clientId")
    val clientId: String,
    @JsonProperty("shopbackData")
    val shopbackData: String
)
