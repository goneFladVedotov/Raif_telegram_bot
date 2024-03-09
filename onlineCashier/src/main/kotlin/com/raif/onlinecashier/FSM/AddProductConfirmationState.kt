package com.raif.onlinecashier.FSM

import com.raif.onlinecashier.Utilities
import org.telegram.telegrambots.meta.api.objects.Update

class AddProductConfirmationState(
    private val stateController: StateController,
    private val name: String,
    private val price: Double
) : State {
    override fun nextState(update: Update): State {
        if (update.hasCallbackQuery()) {
            val query = update.callbackQuery
            val data = query.data
            when (data) {
                "add_product_confirmation_1" -> {
                    stateController.answer(query.id)
                    return MenuState(stateController, 1)
                }

                "add_product_confirmation_2" -> {
                    stateController.answer(query.id)
                    stateController.dataService.addMenuProduct(stateController.chatId, name, price)
                    return MenuState(stateController, 1)
                }
            }
        }
        return this
    }

    override fun show() {
        val text =
            "Пожалуйста, проверьте информацию о товаре, который вы хотите добавить. \n" +
                    "Название: $name\n" +
                    "Цена: $price"
        val markup = Utilities.makeInlineKeyboard(
            listOf(listOf("Отмена"), listOf("Добавить")), "add_product_confirmation"
        )
        stateController.send(text, markup)
    }

}