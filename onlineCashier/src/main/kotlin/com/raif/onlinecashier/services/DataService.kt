package com.raif.onlinecashier.services

import com.raif.onlinecashier.Constants
import com.raif.onlinecashier.models.*
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class DataService(
    private val qrObjectRepository: QrObjectRepository,
    private val menuEntityRepository: MenuEntityRepository,
    private val orderEntityRepository: OrderEntityRepository,
) {

    private val logger = LoggerFactory.getLogger("DataLayer")

    fun addMenuItem(chatId: Long, name: String, price: Double) {
        val entity = MenuEntity(chatId, name, price)
        menuEntityRepository.saveAndFlush(entity)
        logger.info("Add to [$chatId] menu item ($name, $price)")
    }

    fun delMenuItem(id: Int) {
        val orderEnt = orderEntityRepository.findByMenuItemId(id)
        if (orderEnt != null) {
            orderEntityRepository.deleteById(orderEnt.id)
        }
        menuEntityRepository.deleteById(id)
    }

    fun addOrderItem(chatId: Long, id: Int, count: Int) {
        var entity = orderEntityRepository.findByMenuItemId(id)
        if (entity == null) {
            if (count < 0) return
            entity = OrderEntity(chatId, MenuEntity(id), count)
            orderEntityRepository.saveAndFlush(entity)
            logger.info("Add to [$chatId] order item ($id, $count)")
            return
        }
        entity.amount += count
        if (entity.amount <= 0) {
            orderEntityRepository.deleteById(entity.id)
            logger.info("Delete [$chatId, $id] ")
        } else {

            orderEntityRepository.saveAndFlush(entity)
            logger.info("Update to [$chatId, $id] order ($count)")
        }
    }

    fun delOrderItem(id: Int) {
        addOrderItem(0, id, -1)
    }

    fun clearCart(chatId: Long) {
//        orderEntityRepository.deleteByChatId(chatId)
        while (true) {
            val pageable = PageRequest.of(0, 100, Sort.by("menuItem.name").ascending())
            val pageResult = orderEntityRepository.findByChatId(chatId, pageable)
            if (pageResult.isEmpty) break
            for (item in pageResult) {
                orderEntityRepository.deleteById(item.id)
            }
        }
    }

    fun getMenuPageCount(chatId: Long): Int {
        val x = menuEntityRepository.countByChatId(chatId)
        val y = Constants.ITEMS_ON_PAGE
        return maxOf((x + y - 1) / y, 1)
    }

    fun getOrderPageCount(chatId: Long): Int {
        val x = orderEntityRepository.countByChatId(chatId)
        val y = Constants.ITEMS_ON_PAGE
        return maxOf((x + y - 1) / y, 1)
    }

    fun getMenuItems(chatId: Long, page: Int): List<MenuEntity> {
        val pageable = PageRequest.of(page, Constants.ITEMS_ON_PAGE, Sort.by("name").ascending())
        val pageResult = menuEntityRepository.findByChatId(chatId, pageable)
        return pageResult.content
    }

    fun getOrderItems(chatId: Long, page: Int): List<OrderEntity> {
        val pageable = PageRequest.of(page, Constants.ITEMS_ON_PAGE, Sort.by("menuItem.name").ascending())
        val pageResult = orderEntityRepository.findByChatId(chatId, pageable)
        return pageResult.content
    }

    fun getMenuItem(id: Int): MenuEntity? {
        return menuEntityRepository.findById(id).getOrNull()
    }

    fun getOrderItem(id: Int): OrderEntity? {
        return orderEntityRepository.findById(id).getOrNull()
    }

}