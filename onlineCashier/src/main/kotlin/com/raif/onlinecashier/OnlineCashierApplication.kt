package com.raif.onlinecashier

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class OnlineCashierApplication

fun main(args: Array<String>) {
	runApplication<OnlineCashierApplication>(*args)
}
