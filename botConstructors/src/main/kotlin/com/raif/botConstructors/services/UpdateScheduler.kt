package com.raif.botConstructors.services

import com.raif.botConstructors.models.Order
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class UpdateScheduler(
    private val qrCodeService: QRCodeService,
    private val orderService: OrderService,
    private val smartBotProService: SmartBotProService,
    private val botobotService: BotobotService
) {

    private val logger = LoggerFactory.getLogger(javaClass)
    @Scheduled(initialDelay = 1000L, fixedDelay = 10000L)
    fun updateOrderStatus() {
        logger.info("updateOrderStatus")
        qrCodeService.updateAll()
        val orderList: List<Order> = orderService.getAll()
        for (order: Order in orderList) {
            val newStatus: String = qrCodeService.getQR(order.qrId).qrStatus
            if (order.status != newStatus) {
                val newOrder: Order = order.copy(status = newStatus)
                if (newStatus == "PAID" && order.type == "smartbotpro") {
                    smartBotProService.orderPaid(newOrder)
                }
                if (newStatus == "PAID" && order.type == "botobot") {
                    botobotService.paidOrder(newOrder.id)
                }

                orderService.createOrder(newOrder)
            }
        }
    }
}