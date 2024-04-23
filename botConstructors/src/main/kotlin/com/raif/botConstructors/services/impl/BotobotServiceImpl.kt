package com.raif.botConstructors.services.impl

import com.raif.botConstructors.services.BotobotService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap

@Service
class BotobotServiceImpl(
    private val restTemplate: RestTemplate
): BotobotService {

    private val logger = LoggerFactory.getLogger(javaClass)
    private var botobotToken = "v1.76852.tchR1xl3U5cYH3RfXDOAH_6YpmTfxShQ7QELgcMoAZ6qIAzqreO-hdgUB1qFrKiP"
    override fun updateToken(token: String) {
        botobotToken = token
    }

    override fun updateOrderStatus(id: String, status: String) {
        val method = "updateOrderStatus"
        logger.info("BotobotService $method id=$id status=$status")
        val url = "https://www.botobot.ru/api/v1/$method/${botobotToken}"
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        val map = LinkedMultiValueMap<String, String>()
        map.add("id", id)
        map.add("status", status)
        val entity = HttpEntity<LinkedMultiValueMap<String, String>>(map, headers)
        val response = restTemplate.postForEntity(url, entity, String::class.java)
        logger.info("Response Status: ${response.statusCode}")
        logger.info("Response Body: ${response.body}")
    }

    override fun paidOrder(id: String) {
        val method = "paidOrder"
        logger.info("BotobotService $method id=$id")
        updateOrderStatus(id, "50") // 50 — оформлен возврат
        val url = "https://www.botobot.ru/api/v1/$method/${botobotToken}"
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        val map = LinkedMultiValueMap<String, String>()
        map.add("id", id)
        val entity = HttpEntity<LinkedMultiValueMap<String, String>>(map, headers)
        val response = restTemplate.postForEntity(url, entity, String::class.java)
        logger.info("Response Status: ${response.statusCode}")
        logger.info("Response Body: ${response.body}")
    }
}