package com.raif.onlinecashier.FSM

import com.raif.onlinecashier.Utilities
import org.telegram.telegrambots.meta.api.objects.Update
import kotlin.math.max
import kotlin.math.min

class MenuDeletionModeState(
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
                data == "del_menu_left" -> {
                    stateController.answer(query.id)
                    return MenuDeletionModeState(stateController, max(1, page - 1))
                }

                data == "del_menu_right" -> {
                    stateController.answer(query.id)
                    return MenuDeletionModeState(stateController, min(getLength(), page + 1))
                }

                data == "del_menu_add" -> {
                    stateController.answer(query.id)
                    return AddProductEnterNameState(stateController)
                }


                data == "del_menu_delmode" -> {
                    stateController.answer(query.id)
                    return MenuState(stateController, page)
                }

                data == "del_menu_exit" -> {
                    stateController.answer(query.id)
                    return HomeState(stateController)
                }

                data.startsWith("del_menu_") -> {
                    //Add to order
                    stateController.answer(query.id)
                }
            }

        }
        return MenuDeletionModeState(stateController, page)
    }


    override fun show() {
        val text =
            "Каталог товаров (<code>$page/${getLength()}</code>) :\n" +
            "Нажмите на товар, чтобы <b><i>удалить</i></b> его из меню."
        val menu = stateController.dataService.listMenu(stateController.chatId)
        val menuButtons = mutableListOf<List<String>>()
        for ((product, price) in menu) {
            menuButtons.add(listOf("$product ($price руб)"))
        }
        menuButtons.add(listOf("⬅\uFE0F", " ", "\uD83D\uDDD1\uFE0F✅", "➡\uFE0F"))
        menuButtons.add(listOf("Выход↩\uFE0F"))

        val markup = Utilities.makeInlineKeyboard(
            menuButtons, "del_menu", mapOf(
                menu.size + 1 to "del_menu_left",
                menu.size + 2 to "del_menu_add",
                menu.size + 3 to "del_menu_delmode",
                menu.size + 4 to "del_menu_right",
                menu.size + 5 to "del_menu_exit",
            )
        )

        stateController.send(text, markup)


    }
}