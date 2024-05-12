package com.raif.botConstructors

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
class BotConstructorsApplication

fun main(args: Array<String>) {
	runApplication<BotConstructorsApplication>(*args)
}
