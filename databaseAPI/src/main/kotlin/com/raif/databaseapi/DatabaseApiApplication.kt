package com.raif.databaseapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class DatabaseApiApplication

fun main(args: Array<String>) {
    runApplication<DatabaseApiApplication>(*args)
}
