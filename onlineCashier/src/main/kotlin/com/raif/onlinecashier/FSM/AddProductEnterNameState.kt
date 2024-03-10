package com.raif.onlinecashier.FSM

import com.raif.onlinecashier.Constants
import com.raif.onlinecashier.MyInlineButton
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
            } else if (text.length > Constants.ITEM_NAME_MAX_LENGTH) {
                stateController.send("Длина названия не должна привышать ${Constants.ITEM_NAME_MAX_LENGTH} символов")
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
        val markup = Utilities.makeInlineKeyboard(
            listOf(
                listOf(MyInlineButton("Отмена"))
            ), "add_product_name"
        )
        stateController.send(text, markup)
    }

}