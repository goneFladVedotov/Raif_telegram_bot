package com.raif.onlinecashier.services

interface DataService {
    fun test()
    fun addMenuProduct(chatId: Long, name: String, price: Double)
    fun delMenuProduct(chatId: Long, name: String)
    fun addOrderProduct(chatId: Long, name: String)
    fun delOrderProduct(chatId: Long, name: String)
    fun delOrderProduct(chatId: Long, id: Int)
    fun listMenu(chatId: Long): Map<String, Double>
    fun listOrder(chatId: Long): List<String>
}