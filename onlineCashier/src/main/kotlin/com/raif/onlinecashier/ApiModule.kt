package com.raif.onlinecashier

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class MessageController {
    @GetMapping("/")
    fun index(): String {
        val myBot = MyBot()
        myBot.sendMessageExecute("472209097", "abn")
        return "abn"
    }
}