package com.raif.onlinecashier

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Component
class AppConfig {
    @Value("\${telegram.botToken}")
    lateinit var botToken: String
}


@Service
class MyBot : TelegramLongPollingBot() {//TODO move bot token to constructor

    @Value("\${telegram.botName}")
    private val botName = ""
    @Value("\${telegram.botToken}")
    private val Token = ""

    override fun getBotToken(): String = Token
    override fun getBotUsername(): String {
        println("GetName")
        return botName
    }

    override fun onUpdateReceived(update: Update) {

    }

    fun testfunc() {
        val send = SendMessage("472209097", "abn")
        execute(send)
    }

}
@Configuration
class BotConfig {
	@Bean
	fun telegramBotsApi(bot: MyBot): TelegramBotsApi =
		TelegramBotsApi(DefaultBotSession::class.java).apply {
			registerBot(bot)
		}
}