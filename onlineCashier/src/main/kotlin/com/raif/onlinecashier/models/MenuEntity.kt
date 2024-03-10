package com.raif.onlinecashier.models

import jakarta.persistence.*
import jakarta.validation.constraints.Min
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.validation.annotation.Validated


@Entity
@Table(name = "menus")
class MenuEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",
    @Column(nullable = true)
    val chatId: Long = 0,
    @Column(nullable = true)
    val name: String = "",
    @Column(nullable = true)
    @Min(value = 1, message = "amount must be greater than 0")
    val price: Double = 0.0,
) {
    constructor(chatId: Long, name: String, price: Double) : this("", chatId, name, price)
}

@Repository
interface MenuEntityRepository : JpaRepository<MenuEntity, String> {
    fun findByChatId(chatId: Long, page: Pageable): Page<MenuEntity>
}