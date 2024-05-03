package com.raif.paymentapi.service.impl

import com.raif.paymentapi.domain.dto.OrderDto
import com.raif.paymentapi.domain.model.OrderInformation
import com.raif.paymentapi.service.DatabaseApiClient
import com.raif.paymentapi.service.OrderService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import raiffeisen.sbp.sdk.client.SbpClient
import raiffeisen.sbp.sdk.model.`in`.OrderInfo
import raiffeisen.sbp.sdk.model.out.Order
import raiffeisen.sbp.sdk.model.out.OrderId

@Service
class OrderServiceImpl(
    private val databaseApiClient: DatabaseApiClient,
    @Value("\${raif.sbpMerchantId}")
    private val sbpMerchantId: String,
    @Value("\${raif.secretKey}")
    private val secretKey: String
): OrderService {
    override fun makeOrder(orderDto: OrderDto): OrderInfo {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        val order = Order.builder()
            .amount(orderDto.amount)
            .id(orderDto.id)
            .qr(orderDto.orderQr)
            .comment(orderDto.comment)
            .expirationDate(orderDto.expirationDate)
            .build()
        val result = sbpClient.createOrder(order)
        databaseApiClient.save(
            OrderInformation(
                result.id,
                result.amount,
                result.comment,
                result.status.value,
                result.status.date,
                result.expirationDate,
                result.qr.id
            )
        )
        return result
    }

    override fun cancelOrder(orderId: String) {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        sbpClient.orderCancellation(OrderId(orderId))
        databaseApiClient.updateStatus("/database-api/v1/order", orderId, "CANCELLED")
    }
}