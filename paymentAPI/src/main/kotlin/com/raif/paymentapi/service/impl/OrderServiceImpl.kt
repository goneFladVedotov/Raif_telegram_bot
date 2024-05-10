package com.raif.paymentapi.service.impl

import com.raif.paymentapi.domain.dto.OrderDto
import com.raif.paymentapi.domain.model.OrderInformation
import com.raif.paymentapi.service.DatabaseApiClient
import com.raif.paymentapi.service.OrderService
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import raiffeisen.sbp.sdk.client.SbpClient
import raiffeisen.sbp.sdk.model.`in`.OrderInfo
import raiffeisen.sbp.sdk.model.out.Order
import raiffeisen.sbp.sdk.model.out.OrderId
import raiffeisen.sbp.sdk.model.out.OrderQr
import java.util.concurrent.ArrayBlockingQueue

@Service
class OrderServiceImpl(
    private val databaseApiClient: DatabaseApiClient,
    @Value("\${raif.sbpMerchantId}")
    private val sbpMerchantId: String,
    @Value("\${raif.secretKey}")
    private val secretKey: String
): OrderService {
    private val queue = ArrayBlockingQueue<String>(10)
    override fun makeOrder(orderDto: OrderDto): OrderInfo {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        val orderQr = OrderQr()
        orderQr.id = orderDto.qrId
        val order = Order.builder()
            .amount(orderDto.amount)
            .id(orderDto.orderId)
            .qr(orderQr)
            .comment("no_comments")
            .expirationDate(orderDto.expirationDate?: "+5m")
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

        queue.add(order.id)

        return result
    }

    override fun cancelOrder(orderId: String) {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        sbpClient.orderCancellation(OrderId(orderId))
        databaseApiClient.updateStatus("/database-api/v1/orders", orderId, "CANCELLED")
    }

    @Scheduled(fixedDelay = 10000)
    private fun checkOrderStatus() {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        var size = queue.size
        while (size-- > 0) {
            val current = queue.poll()
            val status = sbpClient.getOrderInfo(OrderId(current)).status.value
            if (status != "IN_PROGRESS") {
                databaseApiClient.updateStatus("database-api/v1/orders", current, status)
            } else {
                queue.add(current)
            }
        }
    }

}