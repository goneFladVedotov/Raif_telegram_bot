package com.raif.onlinecashier.services

import org.springframework.beans.factory.annotation.Value
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton


@Service
class TelegramService(
    @Value("\${telegram.botToken}")
    private val botToken: String,
    private val eventPublisher: ApplicationEventPublisher
) : TelegramLongPollingBot(botToken) {

    @Value("\${telegram.botName}")
    private val botName = ""
    override fun getBotUsername(): String {
        return botName
    }

    private val logger = LoggerFactory.getLogger("TelegramService")


    override fun onUpdateReceived(update: Update) {
        logger.info("Received a new update: {}", update)
        eventPublisher.publishEvent(update)
    }

    fun sendMessage(
        chatId: Long,
        text: String,
        replyMarkup: ReplyKeyboard? = null,
        markdown: String = "HTML",
        replyTo: Int? = null
    ): Int {
        val send = SendMessage(chatId.toString(), text)
        send.parseMode = markdown
        if (replyTo != null) {
            send.replyToMessageId = replyTo
        }
        if (replyMarkup != null) {
            send.replyMarkup = replyMarkup
        }
        val e = execute(send)
        return e.messageId
    }

    fun sendPhoto(chatId: Long, url: String, text: String = "", replyTo: Int? = null): Int {
        val send = SendPhoto(chatId.toString(), InputFile(url))
        send.caption = text
        if (replyTo != null) {
            send.replyToMessageId = replyTo
        }
        val e = execute(send)
        return e.messageId
    }

    fun answerCallback(id: String, text: String = "", alert: Boolean = false) {
        val send = AnswerCallbackQuery(id)
        send.text = text
        send.showAlert = alert
        execute(send)

    }


}