package com.raif.botConstructors.services.impl

import com.raif.botConstructors.models.Order
import com.raif.botConstructors.models.dto.ReceiptDto
import com.raif.botConstructors.models.dto.RefundDto
import com.raif.botConstructors.services.ReceiptService
import org.slf4j.LoggerFactory
import org.springframework.web.client.RestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.getForObject
import java.math.BigDecimal

@Service
class ReceiptServiceImpl(private val restTemplate: RestTemplate): ReceiptService {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun createRefundReceipt(order: Order, refundDto: RefundDto) {
        logger.info("createRefundReceipt For ${order.id}")
        try {
            val items: Array<ReceiptDto.ReceiptItemDto> = order.items.map { purchaseItem ->
                ReceiptDto.ReceiptItemDto(
                    name = purchaseItem.name,
                    price = BigDecimal.valueOf(purchaseItem.price),
                    quantity = BigDecimal.valueOf(purchaseItem.quantity),
                    amount = BigDecimal.valueOf(purchaseItem.amount),
                    vatType = purchaseItem.vatType
                )
            }.toTypedArray()
            val receiptNumber: String = order.type + order.id + "refund"
            val receiptDto =
                ReceiptDto(receiptNumber, order.client.email, items, refundDto.amount)
            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON
            val entity = HttpEntity(receiptDto, headers)
            val urlCreate = "http://147.78.66.234:8081/payment-api/v1/receipt/refund"
            restTemplate.postForObject(urlCreate, entity, String::class.java)
            val urlRegister = "http://147.78.66.234:8081/payment-api/v1/receipt/refund/$receiptNumber"
            restTemplate.exchange(urlRegister, HttpMethod.PUT, HttpEntity.EMPTY, String::class.java)
        } catch (e: Exception) {
            logger.error("Error during refund receipt: " + e.message)
        }
    }

    override fun createSellReceipt(order: Order) {
        logger.info("createSellReceipt For ${order.id}")
        try {
            val items: Array<ReceiptDto.ReceiptItemDto> = order.items.map { purchaseItem ->
                ReceiptDto.ReceiptItemDto(
                    name = purchaseItem.name,
                    price = BigDecimal.valueOf(purchaseItem.price),
                    quantity = BigDecimal.valueOf(purchaseItem.quantity),
                    amount = BigDecimal.valueOf(purchaseItem.amount),
                    vatType = purchaseItem.vatType
                )
            }.toTypedArray()
            val receiptNumber: String = order.type + order.id
            val receiptDto =
                ReceiptDto(receiptNumber, order.client.email, items, BigDecimal.valueOf(order.amount))
            logger.info("receiptDto=$receiptDto")
            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON
            val entity = HttpEntity(receiptDto, headers)
            val urlCreate = "http://147.78.66.234:8081/payment-api/v1/receipt/sell"
            restTemplate.postForObject(urlCreate, entity, String::class.java)
            val urlRegister = "http://147.78.66.234:8081/payment-api/v1/receipt/sell/$receiptNumber"
            restTemplate.exchange(urlRegister, HttpMethod.PUT, HttpEntity.EMPTY, String::class.java)
        } catch (e: Exception) {
            logger.error("Error during sell receipt: " + e.message)
        }
    }

    override fun getRefundOfd(id: String): String {
        logger.info("getRefundOfd $id")
        val url = "http://147.78.66.234:8081/payment-api/v1/receipt/refund/$id"
        val response: Map<String, Any> = restTemplate.getForObject(url)
        logger.info("response: $response")
        val ofdUrl = response["ofdUrl"]?.toString() ?: throw IllegalStateException("Field 'ofdUrl' is null")
        return ofdUrl
    }

    override fun getSellOfd(id: String): String {
        logger.info("getSellOfd $id")
        val url = "http://147.78.66.234:8081/payment-api/v1/receipt/sell/$id"
        val response: Map<String, Any> = restTemplate.getForObject(url)
        logger.info("response: $response")
        val ofdUrl = response["ofdUrl"]?.toString() ?: throw IllegalStateException("Field 'ofdUrl' is null")
        return ofdUrl
    }
}