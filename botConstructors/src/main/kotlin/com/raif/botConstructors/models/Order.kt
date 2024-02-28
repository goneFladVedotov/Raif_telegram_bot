package com.raif.botConstructors.models

data class Order(
    val id: String,
    val type: String, //"botobot", "smartbotpro"
    val client: Client,
    val amount: Double,
    val items: List<PurchaseItem>,
    val qrId: String,
    val status: String
)