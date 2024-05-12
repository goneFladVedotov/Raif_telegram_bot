package com.raif.botConstructors.config

import io.swagger.v3.oas.models.OpenAPI
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate



@Configuration
class ApplicationConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
    }

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

}