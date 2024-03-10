package com.raif.onlinecashier.FSM

import com.raif.onlinecashier.Constants
import com.raif.onlinecashier.MyInlineButton
import com.raif.onlinecashier.Utilities
import org.telegram.telegrambots.meta.api.objects.Update
import kotlin.math.max
import kotlin.math.min

class CartState(
    private val stateController: StateController,
    private var page: Int,
) : State {
    private fun getLength(): Int {
        return 3
    }

    override fun nextState(update: Update): State {
        if (update.hasCallbackQuery()) {
            val query = update.callbackQuery
            val (id, params) = Utilities.parseCallback(query, "cart") ?: return this
            when (id) {
                "left" -> {
                    stateController.answer(query.id)
                    return CartState(stateController, page - 1)
                }

                "right" -> {
                    stateController.answer(query.id)
                    return CartState(stateController, page + 1)
                }

                "deleteAll" -> {
                    stateController.answer(query.id)
                    //TODO("DeleteAllState")
//                    return MenuDeletionModeState(stateController, page)
                }

                "exit" -> {
                    stateController.answer(query.id)
                    return HomeState(stateController)
                }

                "addToCart" -> {
                    //Add to order
                    stateController.dataService.addOrderProduct(stateController.chatId, params[0].toString())
                    stateController.answer(query.id, "Товар \"${params[0]}\" пока не успешно добавлен в корзину")
                    return this
                }

                "empty" -> {
                    stateController.answer(query.id)
                    return this
                }
            }

        }
        return this
    }


    override fun show() {
        val pageCount = getLength()
        page = max(1, page)
        page = min(page, pageCount)


        val text =
            "Ваша корзина (<code>$page/$pageCount</code>) :\n" +
            "Нажмите на товар, чтобы добавить его в корзину еще раз."
        val menu = stateController.dataService.getMenuItems(stateController.chatId, page - 1)
        val menuButtons = mutableListOf<List<MyInlineButton>>()
        for (ent in menu) {
            menuButtons.add(listOf(MyInlineButton("${ent.name} (${ent.price} руб)", "addToCart", listOf(ent.name))))
        }
        for (i in menu.size..<Constants.ITEMS_ON_PAGE) {
            menuButtons.add(listOf(MyInlineButton()))
        }
        menuButtons.add(
            listOf(
                MyInlineButton(if (page > 1) "⬅\uFE0F" else " ", "left"),
                MyInlineButton("\uD83D\uDDD1\uFE0F", "deleteAll"),
                MyInlineButton(if (page < pageCount) "➡\uFE0F" else " ", "right")
            )
        )
        menuButtons.add(listOf(MyInlineButton("Выход↩\uFE0F", "exit")))

        val markup = Utilities.makeInlineKeyboard(menuButtons, "cart")
        stateController.send(text, markup)


    }
}