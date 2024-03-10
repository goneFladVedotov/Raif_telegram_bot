package com.raif.onlinecashier.services

import com.raif.onlinecashier.Constants
import com.raif.onlinecashier.models.*
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class DataService(
    private val qrObjectRepository: QrObjectRepository,
    private val menuEntityRepository: MenuEntityRepository,
    private val orderEntityRepository: OrderEntityRepository,
) {

    private val logger = LoggerFactory.getLogger("DataLayer")

    fun addMenuProduct(chatId: Long, name: String, price: Double) {
        val entity = MenuEntity(chatId, name, price)
        menuEntityRepository.saveAndFlush(entity)
        logger.info("Add to [$chatId] menu item ($name, $price)")
    }

    fun delMenuProduct(chatId: Long, name: String) {
        TODO("Not implemented")
    }

    fun addOrderProduct(chatId: Long, id: Int) {
        val entity = OrderEntity(chatId, MenuEntity(id), 1)
        orderEntityRepository.saveAndFlush(entity)
        logger.info("Add to [$chatId] order item ($id)")
    }

    fun delOrderProduct(chatId: Long, name: String) {
        TODO("Waiting for buttons and FSM")
    }

    fun getMenuItems(chatId: Long, page: Int): List<MenuEntity> {
        val pageable = PageRequest.of(page, Constants.ITEMS_ON_PAGE, Sort.by("name").ascending())
        val pageResult = menuEntityRepository.findByChatId(chatId, pageable)
        return pageResult.content
    }

    fun getOrderItems(chatId: Long, page: Int): List<OrderEntity> {
        val pageable = PageRequest.of(page, Constants.ITEMS_ON_PAGE, Sort.by("name").ascending())
        val pageResult = orderEntityRepository.findByChatId(chatId, pageable)
        return pageResult.content
    }

}