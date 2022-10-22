package com.mercadolivro.mercadolivro.event.listener

import com.mercadolivro.mercadolivro.event.PurchaseEvent
import com.mercadolivro.mercadolivro.service.PurchaseService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class GenereteNfeListener (
    private val purchaseService: PurchaseService
        ){

    @Async
    @EventListener
    fun listen(purchaseEvent: PurchaseEvent){
        val nfe = UUID.randomUUID().toString()
        val purchaseModel = purchaseEvent.purchaseModel.copy(nfe = nfe)
        purchaseService.update(purchaseModel)
    }
}