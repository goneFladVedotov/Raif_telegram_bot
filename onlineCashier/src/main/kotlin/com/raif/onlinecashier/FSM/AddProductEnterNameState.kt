package com.raif.onlinecashier.FSM

import com.raif.onlinecashier.Utilities
import org.telegram.telegrambots.meta.api.objects.Update

class AddProductEnterNameState(
    private val stateController: StateController
) : State {
    override fun nextState(update: Update): State {
        if (update.hasMessage()) {
            val text = update.message.text
            if ("\n" in text) {
                stateController.send("Название не должно содержать переносов строки")
            } else if (text.length > MAX_NAME_LENGTH) {
                stateController.send("Длина названия не должна привышать $MAX_NAME_LENGTH символов")
            } else {
                return AddProductEnterPriceState(stateController, text)
            }
        }
        return AddProductEnterNameState(stateController)
    }

    override fun show() {
        val text =
            "Пожалуйста, введите информацию о товаре, который вы хотите добавить. \n" +
                    "Название: ?\n" +
                    "Цена: ?"
        val markup = Utilities.makeInlineKeyboard(listOf(listOf("Отмена")), "add_product_name")
        stateController.send(text, markup)
    }

    companion object {
        const val MAX_NAME_LENGTH = 100
    }
}