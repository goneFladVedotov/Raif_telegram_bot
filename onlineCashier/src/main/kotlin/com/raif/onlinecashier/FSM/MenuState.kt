package com.raif.onlinecashier.FSM

import com.raif.onlinecashier.MyInlineButton
import com.raif.onlinecashier.Utilities
import org.json.JSONException
import org.json.JSONObject
import org.telegram.telegrambots.meta.api.objects.Update
import kotlin.math.max
import kotlin.math.min

class MenuState(
    private val stateController: StateController,
    private val page: Int,
) : State {
    private fun getLength(): Int {
        return 10
    }

    override fun nextState(update: Update): State {
        if (update.hasCallbackQuery()) {
            val query = update.callbackQuery
            val (id, params) = Utilities.parseCallback(query, "menu") ?: return this
            when (id) {
                "left" -> {
                    stateController.answer(query.id)
                    return MenuState(stateController, max(1, page - 1))
                }

                "right" -> {
                    stateController.answer(query.id)
                    return MenuState(stateController, min(getLength(), page + 1))
                }

                "add" -> {
                    stateController.answer(query.id)
                    return AddProductEnterNameState(stateController)
                }


                "delmode" -> {
                    stateController.answer(query.id)
                    return MenuDeletionModeState(stateController, page)
                }

                "exit" -> {
                    stateController.answer(query.id)
                    return HomeState(stateController)
                }

                else -> {
                    //Add to order
                    stateController.answer(query.id)
                }
            }

        }
        return this
    }


    override fun show() {
        val text =
            "Каталог товаров (<code>$page/${getLength()}</code>) :\n" +
                    "Нажмите на товар, чтобы добавить его в корзину."
        val menu = stateController.dataService.listMenu(stateController.chatId)
        val menuButtons = mutableListOf<List<MyInlineButton>>()
        for ((product, price) in menu) {
            menuButtons.add(listOf(MyInlineButton("$product ($price руб)")))
        }
        menuButtons.add(
            listOf(
                MyInlineButton("⬅\uFE0F", "left"),
                MyInlineButton("\uD83C\uDD95", "add"),
                MyInlineButton("\uD83D\uDDD1\uFE0F❌", "delmode"),
                MyInlineButton("➡\uFE0F", "right")
            )
        )
        menuButtons.add(listOf(MyInlineButton("Выход↩\uFE0F", "exit")))

        val markup = Utilities.makeInlineKeyboard(menuButtons, "menu")
        stateController.send(text, markup)


    }
}