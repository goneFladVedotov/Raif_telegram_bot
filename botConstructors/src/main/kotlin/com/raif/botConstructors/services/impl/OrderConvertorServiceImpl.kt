package com.raif.botConstructors.services.impl

import com.raif.botConstructors.models.Client
import com.raif.botConstructors.models.Order
import com.raif.botConstructors.models.PurchaseItem
import com.raif.botConstructors.models.QR
import com.raif.botConstructors.models.dto.BotobotOrderDto
import com.raif.botConstructors.services.ClientService
import com.raif.botConstructors.services.OrderConvertorService
import com.raif.botConstructors.services.QRCodeService
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap

@Service
class OrderConvertorServiceImpl(
    private val clientService: ClientService,
    private val qrCodeService: QRCodeService
): OrderConvertorService {
    override fun toBotobotOrderDto(params: MultiValueMap<String, String>): BotobotOrderDto {
        val id = params.getFirst("id")?.toInt() ?: 0
        val userChatId = params.getFirst("user_chat_id") ?: ""
        val costTotal = params.getFirst("cost_total")?.toFloat() ?: 0f
        val costCart = params.getFirst("cost_cart")?.toFloat() ?: 0f
        val costDiscount = params.getFirst("cost_discount")?.toFloat() ?: 0f
        val costDelivery = params.getFirst("cost_delivery")?.toFloat() ?: 0f
        val delivery = params.getFirst("delivery") ?: ""
        val time = params.getFirst("time") ?: ""
        val recipient = params.getFirst("recipient") ?: ""
        val address = params.getFirst("address") ?: ""
        val mobile = params.getFirst("mobile")
        val email = params.getFirst("email")

        val goods = params.get("goods[0][id]")?.mapIndexed { index, id ->
            val article = params.get("goods[$index][article]")?.get(0) ?: ""
            val title = params.get("goods[$index][title]")?.get(0) ?: ""
            val count = params.get("goods[$index][count]")?.get(0)?.toFloat()?.toInt() ?: 0
            val price = params.get("goods[$index][price]")?.get(0)?.toFloat() ?: 0f
            val discount = params.get("goods[$index][discount]")?.get(0)?.toFloat() ?: 0f
            val total = params.get("goods[$index][total]")?.get(0)?.toFloat() ?: 0f
            BotobotOrderDto.GoodDto(id.toInt(), article, title, count, price, discount, total)
        } ?: emptyList()

        return BotobotOrderDto(
            id = id,
            user_chat_id = userChatId,
            cost_total = costTotal,
            cost_cart = costCart,
            cost_discount = costDiscount,
            cost_delivery = costDelivery,
            delivery = delivery,
            time = time,
            recipient = recipient,
            address = address,
            mobile = mobile,
            email = email,
            goods = goods
        )
    }
    override fun convertBotobotOrderDtoToOrder(order: BotobotOrderDto): Order {
        val client: Client = Client(order.user_chat_id, "botobot", order.email ?: "", order.recipient, {})
        clientService.createClient(client)

        val amount = order.cost_total
        val orderId = "botobot${order.id}"
        val qr: QR = qrCodeService.createQR(amount.toDouble(), orderId)
        val items: List<PurchaseItem> = List(order.goods.size) { index: Int ->
            PurchaseItem(
                order.goods[index].title,
                order.goods[index].price.toDouble(),
                order.goods[index].count.toDouble(),
                order.goods[index].total.toDouble(),
                "NONE")
        }
        return Order(order.id.toString(), "botobot", client, amount.toDouble(), items, qr.qrId, qr.qrStatus)
    }
}