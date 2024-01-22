package com.raif.paymentapi.domain.model

import jakarta.persistence.*

@Entity
@Table(name = "qr_keys")
class QrKey (
    @Column(name = "qr_id")
    val qrId: String
    /*@Column(name = "secret_key")
    val secretKey: String,
    @Column(name = "merchant_id")
    val merchantId: String*/
):BaseEntity() {
}