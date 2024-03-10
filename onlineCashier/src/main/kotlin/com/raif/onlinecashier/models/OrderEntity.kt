package com.raif.onlinecashier.models

import jakarta.persistence.*
import jakarta.validation.constraints.Min
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Entity
@Table(name = "orders")
class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    @Column(nullable = false)
    val chatId: Long = 0,

    @OneToOne(cascade = [CascadeType.REMOVE])
    val menuItem: MenuEntity? = null,
    @Column(nullable = false)
    @Min(value = 1, message = "amount must be greater than 0")
    val amount: Int = 0,
) {
    constructor(chatId: Long, menuItem: MenuEntity, amount: Int) : this(0, chatId, menuItem, amount)
}

@Repository
interface OrderEntityRepository : JpaRepository<OrderEntity, String> {
    fun findByChatId(chatId: Long, page: Pageable): Page<OrderEntity>
}