package com.raif.onlinecashier.models

import jakarta.persistence.*


@Entity
@Table(name = "qrs")
class QrObject (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",
    val qrId: String = "",
    val payload: String = "",
    val qrUrl: String = "",
    val qrStatus: String = "",
)