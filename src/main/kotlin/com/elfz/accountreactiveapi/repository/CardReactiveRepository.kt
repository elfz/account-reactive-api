package com.elfz.accountreactiveapi.repository

import com.elfz.accountreactiveapi.domain.Card
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface CardReactiveRepository : ReactiveMongoRepository<Card, String> {

}
