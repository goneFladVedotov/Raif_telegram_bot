package com.raif.botConstructors.services

import com.raif.botConstructors.models.Order
import com.raif.botConstructors.models.dto.BotobotOrderDto
import org.springframework.util.MultiValueMap

interface OrderConvertorService {
    fun toBotobotOrderDto(params: MultiValueMap<String, String>): BotobotOrderDto
    fun convertBotobotOrderDtoToOrder(order: BotobotOrderDto): Order
}