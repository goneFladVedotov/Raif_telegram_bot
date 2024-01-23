package com.raif.botConstructors.controller

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity

@RestController
@RequestMapping("/api/v1")
class ApiController(private val restTemplate: RestTemplate) {
    @GetMapping("/get")
    fun getResult(
        @RequestParam id: String,
        @RequestParam amount: Double,
        @RequestParam currency: String
    ): ResponseEntity<UrlResource> {
        val url = "http://host.docker.internal:8081/api/v1/qrs/dynamic"
        val request = mapOf("amount" to amount, "order" to "botconstructors$id")
        val response: ResponseEntity<QR> = restTemplate.postForEntity(url, request, QR::class.java)
        val qrUrl: String = response.body?.qrUrl?: "";
        val resource = UrlResource(qrUrl)
        val headers = HttpHeaders()
        headers.contentType = MediaType.IMAGE_JPEG
        headers.contentLength = resource.contentLength()

        return ResponseEntity.ok()
            .headers(headers)
            .body(resource)

    }

    data class QR(
        @JsonProperty("qrId")
        val qrId: String?,
        @JsonProperty("qrStatus")
        val qrStatus: String?,
        @JsonProperty("payload")
        val payload: String?,
        @JsonProperty("qrUrl")
        val qrUrl: String?
    )
}
