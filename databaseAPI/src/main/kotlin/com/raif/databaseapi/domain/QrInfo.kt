package com.raif.databaseapi.domain

import jakarta.persistence.*


@Entity
@Table(name = "qr_info")
class QrInfo(
    @Column(name = "qr_id")
    var qrId: String,
    @Column(name = "qr_status")
    var qrStatus: String,
    @Column(name = "payload")
    var payload: String,
    @Column(name = "qr_url")
    var qrUrl: String
): BaseEntity()
