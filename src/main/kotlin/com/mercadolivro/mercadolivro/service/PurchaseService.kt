package com.mercadolivro.mercadolivro.service

import com.mercadolivro.mercadolivro.enum.BookStatus
import com.mercadolivro.mercadolivro.event.PurchaseEvent
import com.mercadolivro.mercadolivro.model.BookModel
import com.mercadolivro.mercadolivro.model.PurchaseModel
import com.mercadolivro.mercadolivro.repository.BookRepository
import com.mercadolivro.mercadolivro.repository.PurchaseRespository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService (
    private val purchaseRespository: PurchaseRespository,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val bookRepository: BookRepository
        ){

    fun create(purchaseModel: PurchaseModel){
        purchaseRespository.save(purchaseModel)

        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchaseModel))
    }

    fun update(purchaseModel: PurchaseModel) {
        purchaseRespository.save(purchaseModel)
    }

    fun listAll(): MutableList<PurchaseModel> {
        return purchaseRespository.findAll()
    }

    fun findById(id: Int): PurchaseModel{
        return purchaseRespository.findById(id).get()
    }

    fun findSoldBooks(): List<BookModel> {
        return bookRepository.findByStatus(BookStatus.VENDIDO)
    }
}