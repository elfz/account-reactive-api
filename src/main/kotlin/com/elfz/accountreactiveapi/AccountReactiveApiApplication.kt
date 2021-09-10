package com.elfz.accountreactiveapi

import com.elfz.accountreactiveapi.configuration.PartnerProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(value = [PartnerProperties::class])
class AccountReactiveApiApplication

fun main(args: Array<String>) {
    runApplication<AccountReactiveApiApplication>(*args)
}
