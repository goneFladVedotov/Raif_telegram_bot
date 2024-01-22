package com.raif.onlinecashier

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession


@SpringBootApplication
class OnlineCashierApplication

fun main(args: Array<String>) {
	runApplication<OnlineCashierApplication>(*args)
}

//Standard Spring component annotation
//@Service
//class MyBot : TelegramLongPollingBot() {
//
//}
@RestController
class MessageController() {
	@GetMapping("/")
	fun index() : String {
		return "abn";
	}
}
//@Configuration
//class BotConfig {
//	@Bean
//	fun telegramBotsApi(bot: MyBot): TelegramBotsApi =
//		TelegramBotsApi(DefaultBotSession::class.java).apply {
//			registerBot(bot)
//		}
//}