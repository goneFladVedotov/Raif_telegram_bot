package com.raif.onlinecashier

import com.raif.onlinecashier.services.TelegramService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@SpringBootApplication
@EnableScheduling
class OnlineCashierApplication
fun main(args: Array<String>) {
    runApplication<OnlineCashierApplication>(*args)
}

@Configuration
class BotConfig {
    @Bean
    fun telegramBotsApi(bot: TelegramService): TelegramBotsApi =
        TelegramBotsApi(DefaultBotSession::class.java).apply {
            registerBot(bot)
        }
}