package com.raif.botConstructors.models

import com.fasterxml.jackson.annotation.JsonProperty

data class BotobotMethodStatus(
    @JsonProperty("status")
    val status: String,
    @JsonProperty("message")
    val message: String?,
    @JsonProperty("data")
    val data: List<Any>?
)
