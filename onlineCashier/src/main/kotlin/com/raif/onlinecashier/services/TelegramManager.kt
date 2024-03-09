package com.raif.onlinecashier.services

import com.raif.onlinecashier.FSM.InitialState
import com.raif.onlinecashier.FSM.State
import com.raif.onlinecashier.FSM.StateController
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

@Service
class TelegramManager(
    private var telegramService: TelegramService,
    private var dataService: DataService
) {

    private var states: MutableMap<Long, State> = mutableMapOf()


    @EventListener
    fun update(update: Update) {
        if (update.hasMessage()) {
            val chatId = update.message.chatId
            val stateController = StateController(telegramService, dataService, chatId)

            states[chatId] = states.getOrDefault(chatId, InitialState(stateController)).nextState(update)
            states[chatId]?.show()
        }
        if (update.hasCallbackQuery()) {
            val chatId = update.callbackQuery.message.chatId
            val stateController = StateController(telegramService, dataService, chatId)

            states[chatId] = states.getOrDefault(chatId, InitialState(stateController)).nextState(update)
            states[chatId]?.show()
        }

        return
//        if (update.hasMessage()) {
//            val msg = update.message
//            val chatId = msg.chatId
//            val text = msg.text
//
//            if (text.startsWith("/addToMenu")) {
//                val splt = text.split(" ")
//                addToMenu(chatId, splt[1], splt[2].toDouble())
//            } else if (text.startsWith("/addToOrder")) {
//                val splt = text.split(" ")
//                addToOrder(chatId, splt[1])
//            } else if (text == "/listMenu") {
//                getMenu(chatId)
//            } else if (text == "/listOrder") {
//                getOrder(chatId)
//            } else if (text == "/test") {
//                test(chatId)
//            }
//        }
    }


    private fun test(chatId: Long) {
        val keyboard = mutableListOf<List<InlineKeyboardButton>>()
        for (i in 1..500) {
            val button = InlineKeyboardButton("a$i")
            button.callbackData = "as"
            keyboard.add(listOf(button))
        }

        val keyboardMarkup = InlineKeyboardMarkup()

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