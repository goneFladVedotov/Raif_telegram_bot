package com.raif.onlinecashier

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.Message


var bot_token = ""


fun Double.format(digits: Int) = "%.${digits}f".format(this)


fun currentCommandCreate(msg: Message): Boolean {
    val price: Double? = msg.text.toDoubleOrNull()
    return price != null
}

fun parseCommand(msg: Message): MutableList<String> {
    if (msg.text.isEmpty()) {
        return mutableListOf("")
    }
    val normalized = msg.text.replace("\n", " ").trim()
    val args = normalized.split(" ").toMutableList()
    args[0] = args[0].lowercase()
    return args
}

fun currentCommandRefund(msg: Message, params: MutableList<Any>): Boolean {
    params.clear();
    val command = parseCommand(msg)
    if (
        (command.count() == 2) &&
        (command[0] == "/refund" || command[0] == "/возврат") &&
        (command[1].toDoubleOrNull() != null)
        ) {
        params.add(command[1].toDoubleOrNull()!!)
        return true
    }
    return false
}

fun currentCommandHelp(msg: Message): Boolean {
    val command = parseCommand(msg)
    return (command[0] == "/help" || command[0] == "/помощь") && (command.count() == 1)
}


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
        return token
    }

    override fun getBotUsername(): String {
        return botName
    }

    override fun onUpdateReceived(update: Update) {
        if (!update.hasMessage()) return

        val msg = update.message
        val chatId = msg.chatId.toString()
        val param = mutableListOf<Any>();

        if (currentCommandHelp(msg)) {
            sendMessageExecute(
                chatId,
                "Отправьте цену в формате \"123.4\" без лишних символов чтобы создать qr на эту сумму ",
                markdown = "MarkdownV2"
            )
            return
        } else if (currentCommandRefund(msg, param) ) {
            val price = param[0] as Double
            if (!msg.isReply) {
                sendMessageExecute(
                    chatId,
                    "Пожалуйста, отпраьте команду /refund *в ответ* на сообщение бота с qr кодом, оплату по которому нужно вернуть",
                    markdown = "MarkdownV2"
                )
                return
            }
            val repl = msg.replyToMessage

            if (repl.from.userName != botName) {
                sendMessageExecute(
                    chatId,
                    "Пожалуйста, отпраьте команду /refund в ответ *на сообщение бота* с qr кодом, оплату по которому нужно вернуть",
                    markdown = "MarkdownV2"
                )
                return
            }
            if (!repl.hasPhoto() || repl.caption.slice(IntRange(0, 3)) != "qrId") {
                sendMessageExecute(
                    chatId,
                    "Пожалуйста, отпраьте команду /refund в ответ на *сообщение* бота *с qr кодом*, оплату по которому нужно вернуть",
                    markdown = "MarkdownV2"
                )
                return
            }
            val qrId = repl.caption.split("\n")[0].split(" ")[1]
            val refundRes = refund(qrId, chatId, price)
            sendMessageExecute(chatId, refundRes, markdown = "MarkdownV2")

        } else if (currentCommandCreate(msg)) {
            val price: Double = msg.text.toDoubleOrNull() ?: 0.0
            if (price < 10) {
                sendMessageExecute(chatId, "Минимальная сумма чека: 10 рублей")
                return
            }
            val qr = generateQr(price, chatId)
            if (qr == null) {
                sendMessageExecute(chatId, "Ошибка при создании qr")
            } else {
                val replayMsg = "qrId: ${qr.qrId}\nPrice: ${price.format(2)}"
                val replyTo = sendPhotoExecute(chatId, qr.qrUrl, replayMsg)
                val threadWithRunnable = Thread(CheckPayment(chatId, qr.qrId, replyTo))
                threadWithRunnable.start()
            }
        } else {
            sendMessageExecute(
                chatId,
                "Не понимаю, что вы имеете в виду. Пожалуйста, воспользуйтесь командой /help"
            )
        }
    }

    fun sendMessageExecute(chatId: String, text: String, markdown: String? = null, replyTo: Int? = null): Int {
        var new_text = "";
        for (x in text) {
            if (x == '\\' || x == '.' || x == '*' ) {
                new_text += '\\'
            }
            new_text += x
        }
        val send = SendMessage(chatId, new_text)
        if (markdown != null) {
            send.parseMode = markdown
        }
        if (replyTo != null) {
            send.replyToMessageId = replyTo
        }
        val e = execute(send)
        return e.messageId
    }

    fun sendPhotoExecute(chatId: String, url: String, text: String): Int {
        val send = SendPhoto(chatId, InputFile(url))
        send.caption = text
        val e = execute(send)
        return e.messageId
    }
}