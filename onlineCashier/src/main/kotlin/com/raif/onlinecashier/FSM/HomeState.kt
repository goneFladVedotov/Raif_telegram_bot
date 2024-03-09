package com.raif.onlinecashier.FSM

import com.raif.onlinecashier.Utilities
import org.telegram.telegrambots.meta.api.objects.Update

class HomeState(
    private val stateController: StateController
) : State {
    override fun nextState(update: Update): State {
        if (update.hasCallbackQuery()) {
            val query = update.callbackQuery
            if (query.data == "homepage_1") {
                return MenuState(stateController, 1)
            }
        }
        return HomeState(stateController)
    }


    override fun show() {
        val text = "Навигация:"
        val markup = Utilities.makeInlineKeyboard(
            listOf(
                listOf("Меню"),
                listOf("Корзина"),
            ), "homepage"
        )

        stateController.send(text, markup)
    }
}
