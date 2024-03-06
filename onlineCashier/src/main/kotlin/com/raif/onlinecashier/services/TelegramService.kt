package com.raif.onlinecashier.services

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard

interface TelegramService {
    fun sendMessage(chatId: Long, text: String, markdown: String = "HTML", replyTo: Int? = null, replyMarkup: ReplyKeyboard? = null): Int
    fun sendPhoto(chatId: Long, url: String, text: String= "", replyTo: Int? = null): Int

}