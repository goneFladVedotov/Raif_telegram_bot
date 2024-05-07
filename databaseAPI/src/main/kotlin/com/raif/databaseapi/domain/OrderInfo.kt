package com.raif.databaseapi.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "order_info")
class OrderInfo(
    val orderId: String,
    val amount: BigDecimal,
    val comment: String?,
    var status: String,
    @Column(name = "create_date")
    val createDate: String?,
    @Column(name = "expiration_date")
    val expirationDate: String?,
    @Column(name = "qr_id")
    val qrId: String
): BaseEntity() {
}