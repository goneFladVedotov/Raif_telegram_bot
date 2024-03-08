package com.raif.onlinecashier.models

import jakarta.persistence.*


@Entity
@Table(name = "qrs")
class QrObject (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",
    @Column(unique = true, nullable = true)
    val qrId: String = "",
    @Column(unique = true, nullable = true)
    val payload: String = "",
    @Column(unique = true, nullable = true)
    val qrUrl: String = "",
    @Column(nullable = true)
    val qrStatus: String = "",
)