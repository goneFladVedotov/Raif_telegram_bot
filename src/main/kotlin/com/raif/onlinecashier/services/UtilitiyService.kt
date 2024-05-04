package com.raif.onlinecashier.services

import com.raif.onlinecashier.Constants
import org.json.JSONException
import org.json.JSONObject
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

@Service
class UtilitiyService() {

    fun makeInlineKeyboard(
        buttons: List<List<MyInlineButton>>,
        callbackPrefix: String
    ): InlineKeyboardMarkup {
        val keyboard = mutableListOf<MutableList<InlineKeyboardButton>>()
        for (row in buttons) {
            keyboard.add(mutableListOf())
            for (button in row) {
                val inlbutton = InlineKeyboardButton(button.text)
                val callback = JSONObject(
                    mapOf(
                        "id" to "${callbackPrefix}${Constants.CALLBACK_ID_SEPARATOR}${button.id}",
                        "params" to button.params
                    )
                ).toString()
                inlbutton.callbackData = callback
                keyboard.last().add(inlbutton)
            }
        }
        return InlineKeyboardMarkup(keyboard)
    }


    fun parseCallback(query: CallbackQuery, prefix: String): Pair<String, List<Any>>? {
        val data = try {
            JSONObject(query.data)
        } catch (e: JSONException) {
            return null
        }
        var id = data.getString("id")
        val params = data.getJSONArray("params").toList()
        if (!data.getString("id").startsWith("$prefix${Constants.CALLBACK_ID_SEPARATOR}")) {
            return null
        }
        id = id.slice(prefix.length + 1..<id.length)
        return Pair(id, params)

    }
}


class MyInlineButton(
    var text: String,
    var id: String,
    var params: List<Any>,

    ) {
    constructor(text: String, id: String) : this(text, id, listOf())
    constructor(): this(" ", "empty")
}