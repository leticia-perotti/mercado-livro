package com.mercadolivro.mercadolivro.controller.mapper

import com.mercadolivro.mercadolivro.controller.request.PostPurchaseRequest
import com.mercadolivro.mercadolivro.model.PurchaseModel
import com.mercadolivro.mercadolivro.service.BookService
import com.mercadolivro.mercadolivro.service.CostumerService
import org.springframework.stereotype.Component

@Component
class PurchaseMapper (
    private val bookService: BookService,
    private val costumerService: CostumerService
        ){

    fun toModel(request: PostPurchaseRequest):PurchaseModel{
        val costumer = costumerService.returnById(request.costumerId)
        val books = bookService.findAllByIds(request.bookId)

        return PurchaseModel(
            costumer = costumer,
            books = books.toMutableList(),
            price = books.sumOf { it.price }
        )
    }
}