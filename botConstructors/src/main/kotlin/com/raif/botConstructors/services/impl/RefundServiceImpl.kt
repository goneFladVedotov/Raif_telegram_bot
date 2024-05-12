package com.raif.botConstructors.services.impl

import com.raif.botConstructors.models.Order
import com.raif.botConstructors.models.QR
import com.raif.botConstructors.models.dto.RefundDto
import com.raif.botConstructors.services.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.BigDecimal
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private

@Service
class RefundServiceImpl(
    private val qrCodeService: QRCodeService,
    private val smartBotProService: SmartBotProService,
    private val botobotService: BotobotService,
    private val receiptService: ReceiptService,
): RefundService  {

    private val logger = LoggerFactory.getLogger(javaClass)

    val refundSet: MutableSet<String> = mutableSetOf()
    override fun refund(order: Order, amount: BigDecimal, details: String) {
        logger.info("Refund ${order.type} ${order.id}")
        val orderId = order.type + order.id
        if (refundSet.contains(orderId)) {
            logger.info("already refund  ${order.type} ${order.id}")
            return
        }
        val refundDto = RefundDto(orderId, orderId, amount, details, null)
        qrCodeService.refund(refundDto)
        receiptService.createRefundReceipt(order, refundDto);
        refundSet.add(orderId)
        if (order.type == "smartbotpro") {
            smartBotProService.orderRefund(order, refundDto)
        } else if (order.type == "botobot") {
            botobotService.updateOrderStatus(order.id, "50") // 50 — оформлен возврат
        }
    }
}