package com.mercadolivro.mercadolivro.service

import com.mercadolivro.mercadolivro.event.PurchaseEvent
import com.mercadolivro.mercadolivro.model.PurchaseModel
import com.mercadolivro.mercadolivro.repository.PurchaseRespository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService (
    private val purchaseRespository: PurchaseRespository,
    private val applicationEventPublisher: ApplicationEventPublisher
        ){

    fun create(purchaseModel: PurchaseModel){
        purchaseRespository.save(purchaseModel)

        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchaseModel))
    }

    fun update(purchaseModel: PurchaseModel) {
        purchaseRespository.save(purchaseModel)
    }
}