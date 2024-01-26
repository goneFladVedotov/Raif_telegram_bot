package com.raif.paymentapi.service.impl

import com.raif.paymentapi.domain.dto.OrderDto
import com.raif.paymentapi.domain.dto.RefundDto
import com.raif.paymentapi.service.OrderService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import raiffeisen.sbp.sdk.client.SbpClient
import raiffeisen.sbp.sdk.model.out.Order
import raiffeisen.sbp.sdk.model.out.OrderId
import raiffeisen.sbp.sdk.model.out.OrderRefund

@Service
class OrderServiceImpl(
    @Value("\${raif.sbpMerchantId}")
    private val sbpMerchantId: String,
    @Value("\${raif.secretKey}")
    private val secretKey: String
): OrderService {
    override fun makeOrder(orderDto: OrderDto) {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        val order = Order.builder().amount(orderDto.amount).id("test_order").build()
        sbpClient.createOrder(order)
    }

    override fun cancelOrder(orderId: String) {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        sbpClient.orderCancellation(OrderId(orderId))
    }

    override fun refundOrder(refundDto: RefundDto) {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        sbpClient.orderRefund(OrderRefund(refundDto.orderId, refundDto.refundId, refundDto.amount))
    }
}