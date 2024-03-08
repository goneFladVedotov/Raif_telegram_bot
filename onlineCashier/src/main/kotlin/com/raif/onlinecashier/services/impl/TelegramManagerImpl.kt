package com.raif.onlinecashier.services.impl

import com.raif.onlinecashier.services.DataService
import com.raif.onlinecashier.services.TelegramService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class TelegramManagerImpl(
    private var telegramService: TelegramService,
    private var dataService: DataService
) {

    @EventListener
    fun update(update: Update) {
        if (update.hasMessage()) {
            dataService.test()
            val msg = update.message
            val chatId = msg.chatId
            val text = msg.text

            if (text.startsWith("/addToMenu")) {
                val splt = text.split(" ")
                addToMenu(chatId, splt[1], splt[2].toDouble())
            } else if (text.startsWith("/addToOrder")) {
                val splt = text.split(" ")
                addToOrder(chatId, splt[1])
            } else if (text == "/listMenu") {
                getMenu(chatId)
            } else if (text == "/listOrder") {
                getOrder(chatId)
            }
        }
    }

    private fun addToMenu(chatId: Long, name: String, price: Double) {
        telegramService.sendMessage(chatId, "add $name: $price")
        dataService.addMenuProduct(chatId, name, price)
    }

    private fun getMenu(chatId: Long) {
        val menu = dataService.listMenu(chatId)
        telegramService.sendMessage(chatId, menu.toString())
    }

    private fun getOrder(chatId: Long) {
        val order = dataService.listOrder(chatId)
        telegramService.sendMessage(chatId, order.toString())
    }

    private fun addToOrder(chatId: Long, name: String) {
        val menu = dataService.listMenu(chatId)
        if (!menu.contains(name)) {
            telegramService.sendMessage(chatId, "Такого товара нет в меню")
            return
        }
        dataService.addOrderProduct(chatId, name)
        telegramService.sendMessage(chatId, "Товар успешно добавлен в заказ")
    }
}