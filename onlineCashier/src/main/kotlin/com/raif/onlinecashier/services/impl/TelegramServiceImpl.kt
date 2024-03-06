package com.raif.onlinecashier.services.impl

import com.raif.onlinecashier.services.TelegramService
import org.springframework.beans.factory.annotation.Value
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard



@Service
class TelegramServiceImpl(
    @Value("\${telegram.botToken}")
    private val botToken: String,
    private val eventPublisher: ApplicationEventPublisher
) : TelegramLongPollingBot(botToken), TelegramService {

    @Value("\${telegram.botName}")
    private val botName = ""
    override fun getBotUsername(): String {
        return botName
    }
    private val logger = LoggerFactory.getLogger("TelegramService")


    override fun onUpdateReceived(update: Update) {
        logger.debug("Received a new update: {}", update)
        eventPublisher.publishEvent(update)
        if (update.hasMessage()) {
            logger.info("Received message: {}", update.message)

            // Обработка сообщения
        }
    }

    override fun sendMessage(chatId: Long, text: String, markdown: String, replyTo: Int?, replyMarkup: ReplyKeyboard?): Int {
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

    override fun sendPhoto(chatId: Long, url: String, text: String, replyTo: Int?) : Int {
        val send = SendPhoto(chatId.toString(), InputFile(url))
        send.caption = text
        if (replyTo != null) {
            send.replyToMessageId = replyTo
        }
        val e = execute(send)
        return e.messageId
    }


}