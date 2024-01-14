package com.raif.paymentapi.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "refund_keys")
class RefundKey(
    @Column(name = "refund_id")
    val refundId: String,
    @Column(name = "secret_key")
    val secretKey: String,
    @Column(name = "merchant_id")
    val merchantId: String
) : BaseEntity()