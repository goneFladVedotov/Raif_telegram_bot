package com.raif.databaseapi.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.ZonedDateTime

@Entity
@Table(name = "payment_info")
class PaymentInfo(
    @Column(name = "additional_info")
    var additionalInfo: String,
    @Column(name = "amount")
    var amount: BigDecimal,
    @Column(name = "create_date")
    var createDate: ZonedDateTime,
    @Column(name = "currency")
    var currency: String,
    @Column(name = "merchant_id")
    var merchantId: Long,
    @Column(name = "order_info")
    var order: String,
    @Column(name = "payment_status")
    var paymentStatus: String,
    @Column(name = "qr_id")
    var qrId: String,
    @Column(name = "sbp_merchant_id")
    var sbpMerchantId: String,
    @Column(name = "transaction_date")
    var transactionDate: ZonedDateTime,
    @Column(name = "transaction_id")
    var transactionId: Long
) : BaseEntity() {
}