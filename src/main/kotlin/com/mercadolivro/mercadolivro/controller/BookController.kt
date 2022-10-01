package com.mercadolivro.mercadolivro.controller

import com.mercadolivro.mercadolivro.controller.request.PostBookRequest
import com.mercadolivro.mercadolivro.extension.toBookModel
import com.mercadolivro.mercadolivro.service.BookService
import com.mercadolivro.mercadolivro.service.CostumerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("book")
class BookController (
    val costumerService: CostumerService,
    val bookService: BookService
        ){

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun create(
        @RequestBody request: PostBookRequest
    ){
        val costumer = costumerService.returnById(request.costumerId)

        bookService.create(request.toBookModel(costumer))
    }
}