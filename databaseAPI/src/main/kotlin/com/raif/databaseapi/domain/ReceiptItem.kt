package com.raif.databaseapi.domain

import java.math.BigDecimal

class ReceiptItem(
    var name: String,
    var price: BigDecimal,
    var quantity: BigDecimal,
    var amount: BigDecimal,
    var paymentObject: PaymentObject,
    var paymentMode: PaymentMode,
    var measurementUnit: MeasurementUnit,
    var nomenclatureCode: String,
    var vatType: VatType? = null
) {

}
