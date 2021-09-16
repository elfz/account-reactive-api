package com.elfz.accountreactiveapi.service

import com.elfz.accountreactiveapi.configuration.PartnerProperties
import com.elfz.accountreactiveapi.controller.AccountRequest
import com.elfz.accountreactiveapi.domain.Account
import com.elfz.accountreactiveapi.domain.Card
import com.elfz.accountreactiveapi.repository.AccountReactiveRepository
import com.elfz.accountreactiveapi.repository.CardReactiveRepository
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.core.publisher.SignalType
import java.util.logging.Level

@Service
class AccountService(
    private val accountReactiveRepository: AccountReactiveRepository,
    private val cardReactiveRepository: CardReactiveRepository,
    private val partnerProperties: PartnerProperties
) {

    private val webclient : WebClient = WebClient.create()

    fun create(accountRequest: AccountRequest) =
        Mono.just(buildPartnerAccountRequest())
            .flatMap { callPartner(it) }
            .flatMap { buildAccount(it, accountRequest.clientId) }
            .log("AccountService.create", Level.INFO, SignalType.ON_NEXT)
            .flatMap { accountReactiveRepository.save(it) }
            .log("AccountService.create saved", Level.INFO, SignalType.ON_NEXT)
            .log("AccountService.create save failed", Level.INFO, SignalType.ON_ERROR)
            .zipWhen { cardReactiveRepository.save(Card()) }
            .flatMap { Mono.just(it.t1) }

    fun findAll() =
            cardReactiveRepository.findAll()


    private fun callPartner(request: AccountPartnerRequest) = webclient
        .post()
        .uri("${partnerProperties.url}:${partnerProperties.port}/accounts")
        .body(Mono.just(request), AccountPartnerRequest::class.java)
        .retrieve()
        .bodyToMono(AccountPartnerResponse::class.java)


    private fun buildPartnerAccountRequest() = AccountPartnerRequest(
        name = "Elfz name",
        description = "Elfz description",
        product = "123456"
    )

    private fun buildAccount(partnerResponse: AccountPartnerResponse, clientId: String) =
        Mono.just(partnerResponse)
            .map { Account(
                clientId = clientId,
                partnerId = it.id,
                product = it.product
            ) }
}
