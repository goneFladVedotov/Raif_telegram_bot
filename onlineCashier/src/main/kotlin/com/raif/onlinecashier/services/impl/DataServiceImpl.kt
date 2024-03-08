package com.raif.onlinecashier.services.impl

import com.raif.onlinecashier.services.DataService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DataServiceImpl : DataService {

    private val logger = LoggerFactory.getLogger("DataLayer")
    private var menues: MutableMap<Long, MutableMap<String, Double>> = mutableMapOf();
    private var orders: MutableMap<Long, MutableList<String>> = mutableMapOf();

    override fun addMenuProduct(chatId: Long, name: String, price: Double) {
        if (!menues.contains(chatId)) {
            menues[chatId] = mutableMapOf()
        }
        menues[chatId]?.set(name, price)
        logger.info("Add to [$chatId] menu item ($name, $price)")
    }

    override fun delMenuProduct(chatId: Long, name: String) {
        //TODO check if really removed
        menues[chatId]?.remove(name)
        logger.info("Delete from [$chatId] menu item ($name)")
    }

    override fun addOrderProduct(chatId: Long, name: String) {
        if (!orders.contains(chatId)) {
            orders[chatId] = mutableListOf()
        }
        orders[chatId]?.add(name)
        logger.info("Add to [$chatId] order item ($name)")
    }


    override fun delOrderProduct(chatId: Long, name: String) {
        //TODO check if really removed
        orders[chatId]?.remove(name)
        logger.info("Remove from [$chatId] order item ($name)")
    }

    override fun delOrderProduct(chatId: Long, id: Int) {
        //TODO index checks, check if really removed
        orders[chatId]?.removeAt(id)
        logger.info("Remove from [$chatId] order item [$id]")
    }

    override fun listMenu(chatId: Long): Map<String, Double> {
        return menues[chatId] ?: mapOf()
    }

    override fun listOrder(chatId: Long): List<String> {
        return orders[chatId] ?: listOf()
    }

}