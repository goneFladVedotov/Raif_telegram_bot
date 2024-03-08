package com.raif.onlinecashier.services

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

@Service
class TelegramManager(
    private var telegramService: TelegramService,
    private var dataService: DataService
) {

    @EventListener
    fun update(update: Update) {
        if (update.hasMessage()) {
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
            } else if (text == "/test") {
                test(chatId)
            }
        }
    }


    private fun test(chatId: Long) {
        val keyboard = mutableListOf<KeyboardRow>()
        val row1 = KeyboardRow()
        val row2 = KeyboardRow()

        row1.add(KeyboardButton("Button 1"))
        row1.add(KeyboardButton("Button 2"))
        row2.add(KeyboardButton("Button 3"))
        row2.add(KeyboardButton("Button 4"))

        keyboard.add(row1)
        keyboard.add(row2)

        val keyboardMarkup = ReplyKeyboardMarkup()
        keyboardMarkup.keyboard = keyboard

        telegramService.sendMessage(chatId, "aboba", replyMarkup = keyboardMarkup)

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