package com.raif.onlinecashier.services.impl

import com.raif.onlinecashier.services.TelegramService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class TelegramManagerImpl(
    private var telegramService: TelegramService
) {

    @EventListener
    fun update(update: Update) {
        if (update.hasMessage()) {
            val msg = update.message
            if (msg.text.startsWith("/add")) {
                val splt = msg.text.split(" ")
                add(msg.chatId, splt[1], splt[2].toDouble())
            }
        }
    }



    private fun add(chatId: Long, name: String, price: Double) {
        telegramService.sendMessage(chatId, "add $name: $price")
    }
}