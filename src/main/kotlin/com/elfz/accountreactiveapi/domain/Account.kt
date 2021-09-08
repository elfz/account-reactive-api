package com.elfz.accountreactiveapi.domain

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Account(
    val id: String = ObjectId.get().toString(),
    val clientId: String,
    val product: String,
    val partnerId: String
)
