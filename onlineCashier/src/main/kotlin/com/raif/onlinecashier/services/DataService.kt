package com.raif.onlinecashier.services

import com.raif.onlinecashier.models.repositories.QrObjectRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DataService(
    private val qrObjectRepository: QrObjectRepository
) {

    private val logger = LoggerFactory.getLogger("DataLayer")
    private var menues: MutableMap<Long, MutableMap<String, Double>> = mutableMapOf();
    private var orders: MutableMap<Long, MutableList<String>> = mutableMapOf();

    fun test() {
        println(qrObjectRepository.findAll())
    }

    fun addMenuProduct(chatId: Long, name: String, price: Double) {
        if (!menues.contains(chatId)) {
            menues[chatId] = mutableMapOf()
        }
        menues[chatId]?.set(name, price)
        logger.info("Add to [$chatId] menu item ($name, $price)")
    }

    fun delMenuProduct(chatId: Long, name: String) {
        //TODO check if really removed
        menues[chatId]?.remove(name)
        logger.info("Delete from [$chatId] menu item ($name)")
    }

    fun addOrderProduct(chatId: Long, name: String) {
        if (!orders.contains(chatId)) {
            orders[chatId] = mutableListOf()
        }
        orders[chatId]?.add(name)
        logger.info("Add to [$chatId] order item ($name)")
    }


    fun delOrderProduct(chatId: Long, name: String) {
        //TODO check if really removed
        orders[chatId]?.remove(name)
        logger.info("Remove from [$chatId] order item ($name)")
    }

    fun delOrderProduct(chatId: Long, id: Int) {
        //TODO index checks, check if really removed
        orders[chatId]?.removeAt(id)
        logger.info("Remove from [$chatId] order item [$id]")
    }

    fun listMenu(chatId: Long): Map<String, Double> {
        return menues[chatId] ?: mapOf()
    }

    fun listOrder(chatId: Long): List<String> {
        return orders[chatId] ?: listOf()
    }

}