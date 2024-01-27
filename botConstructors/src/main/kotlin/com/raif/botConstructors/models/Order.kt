package com.raif.botConstructors.models

data class Order(
    val id: String,
    val type: String, //"botobot" or "smartbotpro"
    val amount: Double,
    val qr: QR
)