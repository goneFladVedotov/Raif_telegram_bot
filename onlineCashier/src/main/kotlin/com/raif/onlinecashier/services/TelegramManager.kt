package com.raif.onlinecashier.services

import com.raif.onlinecashier.FSM.HomeState
import com.raif.onlinecashier.FSM.InitialState
import com.raif.onlinecashier.FSM.State
import com.raif.onlinecashier.FSM.StateController
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

@Service
class TelegramManager(
    private var telegramService: TelegramService,
    private var dataService: DataService
) {

    private var states: MutableMap<Long, State> = mutableMapOf()


    @EventListener
    fun update(update: Update) {
        if (update.hasMessage()) {
            val chatId = update.message.chatId
            val stateController = StateController(telegramService, dataService, chatId)
            try{
                states[chatId] = states.getOrDefault(chatId, InitialState(stateController)).nextState(update)
            } catch(e: Throwable) {
                states[chatId] = HomeState(stateController)
            }

            states[chatId]?.show()


        }
        if (update.hasCallbackQuery()) {
            val chatId = update.callbackQuery.message.chatId
            val stateController = StateController(telegramService, dataService, chatId)

            try{
                states[chatId] = states.getOrDefault(chatId, InitialState(stateController)).nextState(update)
            } catch (e: Exception) {
                states[chatId] = HomeState(stateController)
            }
            states[chatId]?.show()
        }

        return
    }

    fun test() {
        dataService.addOrderProduct(472209097, 1    )
    }


}
