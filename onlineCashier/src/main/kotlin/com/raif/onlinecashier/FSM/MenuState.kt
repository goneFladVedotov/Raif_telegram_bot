package com.raif.onlinecashier.FSM

import com.raif.onlinecashier.Utilities
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
            val data = query.data
            when {
                data == "menu_left" -> {
                    stateController.answer(query.id)
                    return MenuState(stateController, max(1, page - 1))
                }

                data == "menu_right" -> {
                    stateController.answer(query.id)
                    return MenuState(stateController, min(getLength(), page + 1))
                }

                data == "menu_add" -> {
                    stateController.answer(query.id)
                    return AddProductEnterNameState(stateController)
                }

                data == "menu_exit" -> {
                    stateController.answer(query.id)
                    return HomeState(stateController)
                }

                data.startsWith("menu_") -> {
                    //Add to order
                    stateController.answer(query.id)
                }
            }

        }
        return MenuState(stateController, page)
    }


    override fun show() {
        val text = "Каталог товаров ($page) :"
        val menu = stateController.dataService.listMenu(stateController.chatId)
        val menuButtons = mutableListOf<List<String>>()
        for ((product, price) in menu) {
            menuButtons.add(listOf("$product ($price руб)"))
        }
        menuButtons.add(listOf("⬅\uFE0F", "➕", "➡\uFE0F"))
        menuButtons.add(listOf("Выход↩\uFE0F"))

        val markup = Utilities.makeInlineKeyboard(
            menuButtons, "menu", mapOf(
                menu.size + 1 to "menu_left",
                menu.size + 2 to "menu_add",
                menu.size + 3 to "menu_right",
                menu.size + 4 to "menu_exit",
            )
        )

        stateController.send(text, markup)


    }
}