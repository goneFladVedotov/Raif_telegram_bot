package com.raif.onlinecashier.FSM

import com.raif.onlinecashier.services.DataService
import com.raif.onlinecashier.services.TelegramService
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard

class StateController(
    val telegramService: TelegramService,
    val dataService: DataService,
    val chatId: Long,
) {
    fun send(text: String, replyMarkup: ReplyKeyboard? = null): Int {
        return telegramService.sendMessage(chatId, text, replyMarkup)
    }

    fun answer(id: String) {
        telegramService.answerCallback(id)
    }
}