package com.raif.onlinecashier.models

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Entity
@Table(name = "orders")
class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",
    @Column(nullable = true)
    val chatId: Long = 0,
    @Column(nullable = true)
    val name: String = "",
    @Column(nullable = true)
    val amount: Int = 0,
) {
    constructor(chatId: Long, name: String, amount: Int) : this("", chatId, name, amount)
}

@Repository
interface OrderEntityRepository : JpaRepository<OrderEntity, String> {
    fun findByChatId(chatId: Long): List<OrderEntity>
}