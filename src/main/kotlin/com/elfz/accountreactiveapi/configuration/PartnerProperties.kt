package com.elfz.accountreactiveapi.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("integration.partner")
data class PartnerProperties(

    val url: String,
    val port: Int
)
