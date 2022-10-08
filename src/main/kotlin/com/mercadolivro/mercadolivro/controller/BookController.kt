package com.mercadolivro.mercadolivro.controller

import com.mercadolivro.mercadolivro.controller.request.PostBookRequest
import com.mercadolivro.mercadolivro.controller.request.PutBookRequest
import com.mercadolivro.mercadolivro.controller.response.BookResponse
import com.mercadolivro.mercadolivro.extension.toBookModel
import com.mercadolivro.mercadolivro.extension.toResponse
import com.mercadolivro.mercadolivro.service.BookService
import com.mercadolivro.mercadolivro.service.CostumerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
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
    fun findAll(@PageableDefault(page = 0, size = 10) pageable: Pageable): Page<BookResponse>{
        return  bookService.findAll(pageable).map { it.toResponse() }
    }

    @GetMapping("/ativos")
    fun  findAtivos(@PageableDefault(page = 0, size = 10) pageable: Pageable): List<BookResponse> {
        return bookService.findAtives(pageable).map { it.toResponse() }
    }

    @GetMapping("/{id}")
    fun findbyId(@PathVariable id: Int): BookResponse{
        return bookService.findById(id).toResponse()
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