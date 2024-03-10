package com.raif.onlinecashier.models

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Entity
@Table(name = "qrs")
class QrObject(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",
    @Column(unique = true, nullable = false)
    val qrId: String = "",
    @Column(unique = true, nullable = false)
    val payload: String = "",
    @Column(unique = true, nullable = false)
    val qrUrl: String = "",
    @Column(nullable = false)
    val qrStatus: String = "",
) {
    constructor(qrId: String, payload: String, qrUrl: String, qrStatus: String) :
            this("", qrId, payload, qrUrl, qrStatus)
}

@Repository
interface QrObjectRepository : JpaRepository<QrObject, String> {
}