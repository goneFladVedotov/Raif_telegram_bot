package com.raif.botConstructors.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.raif.botConstructors.models.*
import com.raif.botConstructors.models.dto.ClientSmartbotproDto
import com.raif.botConstructors.models.dto.RefundDto
import com.raif.botConstructors.models.dto.ShopbackDataSmartbotproDto
import com.raif.botConstructors.services.*
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.util.MultiValueMap
import org.springframework.ui.Model
import org.springframework.util.FileCopyUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping("/")
class ApiController(
    private val orderService: OrderService,
    private val qrCodeService: QRCodeService,
    private val clientService: ClientService,
    private val smartBotProService: SmartBotProService,
    private val botobotService: BotobotService,
    private val orderConvertorService: OrderConvertorService,
    private val receiptService: ReceiptService,
    private val refundService: RefundService
) {

    private val logger = LoggerFactory.getLogger(javaClass)
    val objectMapper = ObjectMapper()

    @PostMapping("/bot-constructors/v1/client/create/smartbotpro/")
    fun createClientSmartBotPro(
        @RequestBody request: ClientSmartbotproDto
    ): ResponseEntity<String> {
        logger.info("/bot-constructors/v1/client/create/smartbotpro/")
        clientService.createClient(Client(request.id, "smartbotpro", request.email, request.name, request))
        return ResponseEntity.ok("SUCCESS")
    }

    @GetMapping("/bot-constructors/v1/client/get/smartbotpro/")
    fun getClientSmartBotPro(
        @RequestParam id: String
    ): ResponseEntity<Client> {
        logger.info("/bot-constructors/v1/client/get/smartbotpro/")
        return ResponseEntity.ok(clientService.getClient(id))
    }

    @PostMapping("/bot-constructors/v1/order/create/botobot/", consumes = ["application/x-www-form-urlencoded"])
    fun createOrderBotobot(@RequestParam params: MultiValueMap<String, String>) {
        logger.info("/bot-constructors/v1/order/create/botobot/")
        logger.info("Received POST request with parameters: $params")
        val status = params["status"]?.get(0)
        logger.info("Order status=$status")
        if (status == "10") { // Ожидает обработки
            val botobotOrderDto = orderConvertorService.toBotobotOrderDto(params)
            val order: Order = orderConvertorService.convertBotobotOrderDtoToOrder(botobotOrderDto)
            logger.info("order=$order")
            orderService.createOrder(order)
        }
        if (status == "50") { //оформлен возврат
            logger.info("Do refund from Botobot Info")

            try {
                val botobotOrderDto = orderConvertorService.toBotobotOrderDto(params)
                val orderId = "botobot${botobotOrderDto.id}"
                val order = orderService.getOrder(orderId)
                refundService.refund(order, BigDecimal(order.amount), "full refund from botobot")
            } catch (e: Exception) {
                logger.error(e.message)
            }
        }
    }

    @GetMapping("/bot-constructors/v1/receipt/{type}/{id}", produces = ["text/html"])
    fun receipt(
        @PathVariable("type") type: String,
        @PathVariable("id") id: String
    ): String {
        logger.info("/bot-constructors/v1/receipt/$type/$id")
        try {
            var recieptUrl: String
            var count = 0;
            while (true) {
                try {
                    val url = receiptService.getSellOfd(type + id);
                    recieptUrl = url
                    break
                } catch (e: Exception) {
                    logger.info("Error during odf url")
                }
                count += 1
                if (count > 10) {
                    throw Exception("Не получилось найти ссылку на ОФД. Обновите страницу")
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(300)
                } catch (sleepException: InterruptedException) {
                    println("Error during sleep: ${sleepException.message}")
                }
            }
            val resource = ClassPathResource("templates/redirect.html")
            var content = String(FileCopyUtils.copyToByteArray(resource.inputStream), StandardCharsets.UTF_8)
            content = content.replace("https://example.com", recieptUrl)
            return content
        } catch (e: Exception) {
            return "Fail: ${e.message}"
        }
    }
    @GetMapping("/bot-constructors/v1/order/pay/{type}/{id}", produces = ["text/html"])
    fun orderPay(
        @PathVariable("type") type: String,
        @PathVariable("id") id: String
    ): String {
        logger.info("/bot-constructors/v1/order/pay/$type/$id")
        try {
            val orderId = type + id
            val order: Order = orderService.getOrder(orderId)
            val qr: QR = qrCodeService.getQR(order.qrId)
            logger.info("status=${qr.qrStatus}")
            if (qr.qrStatus == "NEW") {
                val resource = ClassPathResource("templates/pay.html")
                var content = String(FileCopyUtils.copyToByteArray(resource.inputStream), StandardCharsets.UTF_8)
                content = content.replace("qrUrl", qr.qrUrl)
                content = content.replace("order_id", id)
                content = content.replace("payload", qr.payload)
                if (type != "botobot") {
                    content = content.replace("botobot", type)
                }
                return content
            } else if (qr.qrStatus == "PAID") {
                val resource = ClassPathResource("templates/paid.html")
                var content = String(FileCopyUtils.copyToByteArray(resource.inputStream), StandardCharsets.UTF_8)
                content = content.replace("order_id", id)
                content = content.replace("reciept_link", "http://147.78.66.234/bot-constructors/v1/receipt/$type/$id")
                return content
            } else if (qr.qrStatus == "EXPIRED") {
                return "Заказ отменен"
            }
        } catch (e: Exception) {
            return "Failed to get order: ${e.message}"
        }
        return "Error"
    }

    @PostMapping("/bot-constructors/v1/order/create/smartbotpro/")
    fun createOrderSmartBotPro(
        @RequestBody data: String
    ): ResponseEntity<QR> {
        logger.info("/bot-constructors/v1/order/create/smartbotpro/")
        val clientId: String
        val shopbackData: ShopbackDataSmartbotproDto
        try {
            logger.info("data=$data")
            val requestData: getRequestSmartBotPro = objectMapper.readValue(data, getRequestSmartBotPro::class.java)
            logger.info("request=$requestData")
            clientId = requestData.clientId
            logger.info("clientId=$clientId")
            val dataString = requestData.shopbackData.replace("'", "\"").replace("\n", "\\n")
            shopbackData = objectMapper.readValue(dataString, ShopbackDataSmartbotproDto::class.java)
            logger.info("shopbackData=$shopbackData")
        } catch (e: Exception) {
            logger.error("Data Parser ERROR")
            logger.error(e.message)
            throw Exception(e.message)
        }
        val client: Client = clientService.getClient(clientId)
//        val shopbackData = request.shopbackData
        val orderId = "smartbotpro${shopbackData.order_id}"
        val amount = shopbackData.total
        val qr: QR = qrCodeService.createQR(amount, orderId)
        val items: List<PurchaseItem> = List(shopbackData.products.size) { index: Int ->
            PurchaseItem(
                shopbackData.products[index].name,
                shopbackData.products[index].price,
                shopbackData.products[index].count,
                shopbackData.products[index].price * shopbackData.products[index].count,
                "NONE")
        }
        val order: Order = Order(shopbackData.order_id, "smartbotpro", client, amount, items, qr.qrId, qr.qrStatus)
        orderService.createOrder(order)
        return ResponseEntity.ok(qr)
    }

    @GetMapping("/bot-constructors/v1/order/status/{type}/")
    fun orderStatus(
        @PathVariable("type") type: String,
        @RequestParam id: String
    ): ResponseEntity<String> {
        logger.info("/bot-constructors/v1/order/status/$type/")
        val orderId = type + id
        val order: Order = orderService.getOrder(orderId)
        val qr: QR = qrCodeService.getQR(order.qrId)
        return ResponseEntity.ok(qr.qrStatus)
    }

    @GetMapping("/bot-constructors/v1/order/refund/{type}/")
    fun orderRefund(
        @PathVariable("type") type: String,
        @RequestParam id: String,
        @RequestParam refundId: String,
        @RequestParam amount: BigDecimal,
        @RequestParam details: String
        ): ResponseEntity<String> {
        logger.info("/bot-constructors/v1/order/refund/$type/")
        val orderId = type + id
        val order = orderService.getOrder(orderId)
        return ResponseEntity.ok("SUCCESS")
    }

    @GetMapping("/bot-constructors/v1/orders")
    fun getAllOrders(): List<Order> {
        return orderService.getAll()
    }
    @GetMapping("/bot-constructors/v1/clients")
    fun getAllClients(): List<Client> {
        return clientService.getAll()
    }

    @GetMapping("/{token}") //Нужно для валидации smartbotpro
    @ResponseBody
    fun tokenSmartBotPro(
        @PathVariable("token") token: String
    ): String {
        val new_token = token.substring(0, token.length - 5)
        return "<html><body><p id=\"token\">$new_token</p></body></html>"
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
    }
}
