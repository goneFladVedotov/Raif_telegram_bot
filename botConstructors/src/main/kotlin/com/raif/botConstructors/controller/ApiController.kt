package com.raif.botConstructors.controller

import com.raif.botConstructors.models.QR
import com.raif.botConstructors.services.OrderService
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
class ApiController(private val orderService: OrderService) {

    val idList = mutableListOf<Int>()

    @GetMapping("/api/v1/get/botobot/") //Показывает страницу с qr-кодом
    fun getBotobot(
        @RequestParam id: String,
        @RequestParam amount: Double,
        @RequestParam currency: String
    ): ResponseEntity<UrlResource> {
        val qr = orderService.createOrder(id, "botobot", amount).qr;
        val qrUrl: String = qr.qrUrl
        val resource = UrlResource(qrUrl)
        val headers = HttpHeaders()
        headers.contentType = MediaType.IMAGE_JPEG
        headers.contentLength = resource.contentLength()

        return ResponseEntity.ok()
            .headers(headers)
            .body(resource)
    }

    @GetMapping("/api/v1/get/smartbotpro/")
    fun getSmartBotPro(
        @RequestParam id: String,
        @RequestParam amount: Double
    ): ResponseEntity<QR> {
        val qr = orderService.createOrder(id, "smartbotpro", amount).qr;
        return ResponseEntity.ok(qr)
    }

    @GetMapping("/api/v1/status/smartbotpro/")
    fun statusSmartBotPro(
        @RequestParam id: String
    ): String {
        return orderService.getStatus("smartbotpro$id")
    }

    @GetMapping("/65b50d8fc78560b0ff505d62.html") //Нужно для валидации smartbotpro
    @ResponseBody
    fun tokenSmartBotPro(): String {
        return "<html><body><p id=\"token\">65b50d8fc78560b0ff505d62</p></body></html>"
    }
}
