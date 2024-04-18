package com.raif.databaseapi.domain

import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "receipt_info")
class ReceiptInfo(
    var receiptNumber: String,
    var receiptType: String,
    var status: String,
    var receiptClient: ReceiptClient,
    var receiptItems: Array<ReceiptItem?>,
    var receiptPayments: Array<ReceiptPayment?>,
    var total: BigDecimal,
    var ofdUrl: String
) : BaseEntity() {
}