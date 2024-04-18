package com.raif.databaseapi.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "refund_info")
class RefundInfo(
    @Column(name = "amount")
    var amount: BigDecimal,
    @Column(name = "order_info")
    var order: String,
    @Column(name = "refund_id")
    var refundId: String,
    @Column(name = "status")
    var status: String,
    @Column(name = "payment_details")
    var paymentDetails: String,
    @Column(name = "transaction_id")
    var transactionId: Long
) : BaseEntity() {
}