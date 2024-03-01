package com.raif.botConstructors.services.impl

import com.raif.botConstructors.models.Order
import com.raif.botConstructors.models.dto.RefundDto
import com.raif.botConstructors.models.dto.SmartBotProWebHookDto
import com.raif.botConstructors.services.ClientService
import com.raif.botConstructors.services.SmartBotProService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate
import kotlin.reflect.full.memberProperties

@Service
class SmartBotProServiceImpl(
    private val restTemplate: RestTemplate
): SmartBotProService {

    private val logger = LoggerFactory.getLogger(javaClass)
    override fun orderPaid(order: Order) {
        logger.info("Smartbotpro order ${order.id} was paid")
        val usernameProperty = order.client.extraInfo::class.memberProperties.find { it.name == "username" }
        if (usernameProperty == null) {
            throw Exception("client does not have username")
        }
        val username: String = usernameProperty.getter.call(order.client.extraInfo) as String
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val webHookDto = SmartBotProWebHookDto(
            access_token = "KxstcpiIzBNMK8Wb3tV8xei2A9Wuie3XNSholMBtidYj5EvvP4FIeYl_5pkRM-ym",
            v = "0.0.1",
            channel_id = "6791683941",
            block_id = "e22b977e3cc64419735c8e3d",
            peer_id = username,
            data = SmartBotProWebHookDto.Data(
                order = order.id,
                info = "Заказ успешно оплачен"
            )
        )
        val entity = HttpEntity(webHookDto, headers)

        restTemplate.postForEntity(
            "https://api.smartbotpro.ru/blocks/execute",
            entity,
            String::class.java
        )
    }
    override fun orderRefund(order: Order, refundDto: RefundDto) {
        val usernameProperty = order.client.extraInfo::class.memberProperties.find { it.name == "username" }
        if (usernameProperty == null) {
            throw Exception("client does not have username")
        }
        val username: String = usernameProperty.getter.call(order.client.extraInfo) as String
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val webHookDto = SmartBotProWebHookDto(
            access_token = "KxstcpiIzBNMK8Wb3tV8xei2A9Wuie3XNSholMBtidYj5EvvP4FIeYl_5pkRM-ym",
            v = "0.0.1",
            channel_id = "6791683941",
            block_id = "e22b977e3cc64419735c8e3d",
            peer_id = username,
            data = SmartBotProWebHookDto.Data(
                order = order.id,
                info = "Оформлен возврат на сумму ${refundDto.amount} с информацией ${refundDto.paymentDetails}"
            )
        )
        val entity = HttpEntity(webHookDto, headers)

        restTemplate.postForEntity(
            "https://api.smartbotpro.ru/blocks/execute",
            entity,
            String::class.java
        )
    }
}