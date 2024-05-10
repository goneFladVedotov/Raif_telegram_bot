package com.raif.paymentapi.config

import io.swagger.v3.oas.models.OpenAPI
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableScheduling
class ApplicationConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
    }
}