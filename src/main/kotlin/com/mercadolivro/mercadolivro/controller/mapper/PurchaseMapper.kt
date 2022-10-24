package com.mercadolivro.mercadolivro.controller.mapper

import com.mercadolivro.mercadolivro.controller.request.PostPurchaseRequest
import com.mercadolivro.mercadolivro.controller.response.LivrosVendaResponse
import com.mercadolivro.mercadolivro.controller.response.PurchaseResponse
import com.mercadolivro.mercadolivro.enum.BookStatus
import com.mercadolivro.mercadolivro.enum.Errors
import com.mercadolivro.mercadolivro.exception.BadRequestException
import com.mercadolivro.mercadolivro.model.BookModel
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

        request.bookId.map {
            val book = bookService.findById(it)
            if (book.status != BookStatus.ATIVO){
                BadRequestException(Errors.ML301.message, Errors.ML301.code)
            }
        }

        val costumer = costumerService.returnById(request.costumerId)
        val books = bookService.findAllByIds(request.bookId)

        return PurchaseModel(
            costumer = costumer,
            books = books.toMutableList(),
            price = books.sumOf { it.price }
        )
    }

    fun toResponse(request: PurchaseModel): PurchaseResponse {
        val livros : List<LivrosVendaResponse> = mutableListOf()
        for (i in request.books){
            livros.plus(
                LivrosVendaResponse(
                    livro = i.name,
                    valor = i.price
                )
            )
        }
        return PurchaseResponse(
            cliente = request.costumer.name,
            valorTotal = request.price,
            listaLivros = livros
        )
    }

    fun toResponseBook(book: BookModel): LivrosVendaResponse{
        return LivrosVendaResponse(
            livro = book.name,
            valor = book.price
        )
    }
}