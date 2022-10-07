package com.mercadolivro.mercadolivro.controller

import com.mercadolivro.mercadolivro.controller.request.PostBookRequest
import com.mercadolivro.mercadolivro.controller.request.PutBookRequest
import com.mercadolivro.mercadolivro.extension.toBookModel
import com.mercadolivro.mercadolivro.model.BookModel
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

    @GetMapping
    fun findAll(): List<BookModel>{
        return  bookService.findAll()
    }

    @GetMapping("/ativos")
    fun  findAtivos(): List<BookModel>{
        return bookService.findAtives()
    }

    @GetMapping("/{id}")
    fun findbyId(@PathVariable id: Int): BookModel{
        return bookService.findById(id)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int){
        bookService.delete(id)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update (@PathVariable id: Int, @RequestBody book: PutBookRequest){
        val bookSaved = bookService.findById(id)
        bookService.update(book.toBookModel(bookSaved))
    }
}