package com.raif.onlinecashier

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.Message
import java.io.FileInputStream


var bot_token= ""



fun Double.format(digits: Int) = "%.${digits}f".format(this)



@Service
class MyBot : TelegramLongPollingBot() {//TODO move bot token to constructor

    @Value("\${telegram.botName}")
    private val botName = ""
    @Value("\${telegram.botToken}")
    private var token = ""

    override fun getBotToken(): String {
        if (token != "") {
            bot_token = token
        } else {
            token = bot_token
        }
        println("TOKEN: $token")
        return token
    }
    override fun getBotUsername(): String {
        println("GetName")
        return botName
    }

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage()) {
            val msg = update.message
            val chatId = msg.chatId.toString()
            if (false) {

            } else {
                val price: Double = msg.text.toDoubleOrNull() ?: 0.0
                if (price < 10) {
                    sendMessageExecute(chatId, "Минимальная сумма чека: 10 рублей")
                    return
                }
                val qr = generateQr(price, chatId)
                val replay_msg = "qrId: ${qr.id}\nPrice: ${price.format(2)}"
                sendPhotoExecute(chatId, qr.qrUrl, replay_msg)

            }

        }
    }

    fun sendMessageExecute(chatId: String, text: String) {
        val send = SendMessage(chatId, text)
        execute(send)
    }
    fun sendPhotoExecute(chatId: String, url:String, text: String) {
        val send = SendPhoto(chatId, InputFile(url))
        send.caption = text
        execute(send)
    }
    fun testfunc() {
        val send = SendMessage("472209097", "abn")
        execute(send)
    }
    fun notEnough() {

    }
}

