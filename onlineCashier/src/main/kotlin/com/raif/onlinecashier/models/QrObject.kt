package com.raif.onlinecashier.models

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


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

@Repository
interface QrObjectRepository : JpaRepository<QrObject, String> {
}