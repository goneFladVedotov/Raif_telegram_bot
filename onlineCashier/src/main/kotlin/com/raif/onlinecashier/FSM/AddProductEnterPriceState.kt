package com.raif.onlinecashier.FSM

import com.raif.onlinecashier.MyInlineButton
import com.raif.onlinecashier.Utilities
import org.telegram.telegrambots.meta.api.objects.Update

class AddProductEnterPriceState(
    private val stateController: StateController,
    private val name: String
) : State {
    override fun nextState(update: Update): State {
        if (update.hasMessage()) {
            val price = update.message.text.toDoubleOrNull()
            if (price == null) {
                stateController.send("Введите дробное число")
            } else {
                return AddProductConfirmationState(stateController, name, price)
            }
        }
        return AddProductEnterPriceState(stateController, name)
    }

    override fun show() {
        val text =
            "Пожалуйста, введите информацию о товаре, который вы хотите добавить. \n" +
                    "Название: $name\n" +
                    "Цена: ?"
        val markup = Utilities.makeInlineKeyboard(
            listOf(
                listOf(MyInlineButton("Отмена"))
            ), "add_product_price"
        )
        stateController.send(text, replyMarkup = markup)
    }

}