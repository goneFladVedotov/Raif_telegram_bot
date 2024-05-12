package com.raif.botConstructors.models

data class PurchaseItem(
    val name: String,
    val price: Double,
    val quantity: Double,
    val amount: Double, //amount = quantity * price
    val vatType: String //Enum: "NONE" "VAT0" "VAT10" "VAT110" "VAT20" "VAT120"
)
