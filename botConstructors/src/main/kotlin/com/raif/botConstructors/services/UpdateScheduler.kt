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
    private val botobotService: BotobotService,
    private val receiptService: ReceiptService
) {

    private val logger = LoggerFactory.getLogger(javaClass)
    @Scheduled(initialDelay = 1000L, fixedDelay = 1500L)
    fun updateOrderStatus() {
        var needToUpdate: Boolean = false
        for (order: Order in orderService.getAll()) {
            if (order.status == "NEW") {
                needToUpdate = true
            }
        }
        if (!needToUpdate) {
//            logger.info("No need for update")
            return
        }
        logger.info("updateOrderStatus")
        qrCodeService.updateAll()
        val orderList: List<Order> = orderService.getAll()
        for (order: Order in orderList) {
            val newStatus: String = qrCodeService.getQR(order.qrId).qrStatus
            if (order.status != newStatus) {
                val newOrder: Order = order.copy(status = newStatus)
                if (newStatus == "PAID" && order.type == "smartbotpro") {
                    receiptService.createSellReceipt(newOrder)
                    smartBotProService.orderPaid(newOrder)
                }
                if (newStatus == "PAID" && order.type == "botobot") {
                    receiptService.createSellReceipt(newOrder)
                    botobotService.paidOrder(newOrder.id)
                }

                orderService.createOrder(newOrder)
            }
        }
    }
}