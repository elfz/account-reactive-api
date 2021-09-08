package com.elfz.accountreactiveapi.domain

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Card(
    val id: String = ObjectId.get().toString(),
    val description: String = "Card"
)
