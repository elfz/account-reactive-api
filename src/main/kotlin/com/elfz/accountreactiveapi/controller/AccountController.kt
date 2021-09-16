package com.elfz.accountreactiveapi.controller

import com.elfz.accountreactiveapi.domain.Account
import com.elfz.accountreactiveapi.service.AccountService
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/reactive/accounts")
class AccountController(
    private val accountService: AccountService
) {

    @PostMapping
    @CircuitBreaker(name = "Fefe")
    fun createAccount(@RequestBody account: AccountRequest): Mono<Account> =
        Mono.just(account)
            .flatMap { accountService.create(it) }

    @GetMapping
    fun findAccounts(@RequestBody account: AccountRequest) =
            accountService.findAll()

}
