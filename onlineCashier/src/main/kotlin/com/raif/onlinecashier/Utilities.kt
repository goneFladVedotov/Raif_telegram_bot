package com.raif.onlinecashier

import org.json.JSONException
import org.json.JSONObject
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

class Utilities {
    companion object {
        fun makeInlineKeyboard(
            buttons: List<List<MyInlineButton>>,
            callbackPrefix: String
        ): InlineKeyboardMarkup {
            val keyboard = mutableListOf<MutableList<InlineKeyboardButton>>()
            var i = 0
            for (row in buttons) {
                keyboard.add(mutableListOf())
                for (button in row) {
                    i += 1
                    val inlbutton = InlineKeyboardButton(button.text)
                    if (button.id == "") {
                        button.id = "$i"
                    }

                    val callback = JSONObject(
                        mapOf(
                            "id" to callbackPrefix + "|${button.id}",
                            "params" to button.params
                        )
                    ).toString()
                    println(callback)
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
            if (!data.getString("id").startsWith("$prefix|")) {
                return null
            }
            id = id.slice(prefix.length + 1..<id.length)
            return Pair(id, params)

        }
    }
}

class MyInlineButton(
    var text: String,
    var id: String,
    var params: List<Any>,

    ) {
    constructor(text: String, id: String) : this(text, id, listOf())
    constructor(text: String, params: List<Any>) : this(text, "", params)
    constructor(text: String) : this(text, "", listOf())

}