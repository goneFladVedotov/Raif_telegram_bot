package com.raif.onlinecashier

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

class Utilities {
    companion object {
        fun makeInlineKeyboard(
            text: List<List<String>>,
            callbackPrefix: String,
            callback: Map<Int, String> = mapOf()
        ): InlineKeyboardMarkup {
            val keyboard = mutableListOf<MutableList<InlineKeyboardButton>>()
            var i = 0
            for (row in text) {
                keyboard.add(mutableListOf())
                for (butText in row) {
                    i += 1
                    val button = InlineKeyboardButton(butText)
                    var cb = callbackPrefix + "_$i"
                    if (callback.contains(i)) {
                        cb = callbackPrefix + "_${callback[i]!!}"
                    }
                    button.callbackData = cb
                    keyboard.last().add(button)
                }
            }
            return InlineKeyboardMarkup(keyboard)
        }
    }
}