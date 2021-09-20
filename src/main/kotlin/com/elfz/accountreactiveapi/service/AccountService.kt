package com.elfz.accountreactiveapi.service

import com.elfz.accountreactiveapi.configuration.PartnerProperties
import com.elfz.accountreactiveapi.controller.AccountRequest
import com.elfz.accountreactiveapi.domain.Account
import com.elfz.accountreactiveapi.repository.AccountReactiveRepository
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.core.publisher.SignalType
import java.util.logging.Level

@Service
class AccountService(
    private val accountReactiveRepository: AccountReactiveRepository,
    private val partnerProperties: PartnerProperties
) {

    private val webclient : WebClient = WebClient.create()

    fun create(accountRequest: AccountRequest) =
        Mono.just(buildPartnerAccountRequest())
            .log("AccountService.create call partner starting", Level.INFO, SignalType.ON_NEXT)
            .flatMap { callPartner(it) }
            .log("AccountService.create call partner finished", Level.INFO, SignalType.ON_NEXT)
            .flatMap { buildAccount(it, accountRequest.clientId) }
            .log("AccountService.create call database starting", Level.INFO, SignalType.ON_NEXT)
            .flatMap { accountReactiveRepository.save(it) }
            .log("AccountService.create call database saved", Level.INFO, SignalType.ON_NEXT)


    fun findAll() =
            accountReactiveRepository.findAll()


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
