package com.raif.botConstructors.models.dto

data class SmartBotProWebHookDto(
    val access_token: String,
    val v: String,
    val channel_id: String,
    val block_id: String,
    val peer_id: String,
    val data: Data
) {
    data class Data(
        val order: String,
        val info: String
    )
}