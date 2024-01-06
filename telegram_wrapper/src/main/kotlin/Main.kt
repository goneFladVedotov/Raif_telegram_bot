package com.raif


import com.beust.klaxon.*
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.extensions.filters.Filter
import java.io.IOException

fun main() {
    val parser: Parser = Parser.default()
    val jsonData = object {}.javaClass.getResourceAsStream("/local.config.json")
        ?: throw IOException("can't find config file")
    val config: JsonObject = parser.parse(jsonData) as JsonObject
    val botToken = if (config.string("run_mode")?.lowercase() == "debug") {
        config.string("bot_token_debug") ?: throw Exception("Bot token has not been provided")
    } else {
        config.string("bot_token") ?: throw Exception("Bot token has not been provided")
    }
    println("Start bot with token: \"$botToken\"")

    val bot = bot {
        token = botToken
        dispatch {
            message(Filter.Text) {
                val msg = message.text ?: return@message
                val available = "1234567890,."
                var isPrice = true
                for (x in msg) {
                    if (x !in available) {
                        isPrice = false
                    }
                }
                if (isPrice) {
                    bot.sendMessage(
                        chatId = ChatId.fromId(message.chat.id),
                        text = "You have entered a valid price: $msg"
                    )
                } else {
                    bot.sendMessage(
                        chatId = ChatId.fromId(message.chat.id),
                        text = "You have entered a non-valid price. Please enter it either like 123.45 or 123,45"
                    )
                }
            }
            message(!Filter.Text) {
                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = "Please, write price as a plain text"
                )
            }
        }
    }

    bot.startPolling()
}