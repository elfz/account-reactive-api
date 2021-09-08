package com.elfz.accountreactiveapi.repository

import com.elfz.accountreactiveapi.domain.Account
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface AccountReactiveRepository : ReactiveMongoRepository<Account, String> {

    fun findAccountByClientId(clientId: String): Mono<Account>
}
