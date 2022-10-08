package com.mercadolivro.mercadolivro.service

import com.mercadolivro.mercadolivro.enum.BookStatus
import com.mercadolivro.mercadolivro.model.BookModel
import com.mercadolivro.mercadolivro.model.CostumerModel
import com.mercadolivro.mercadolivro.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.awt.print.Pageable

@Service
class BookService(
    val bookRepository: BookRepository
) {
    fun create(book: BookModel) =
        bookRepository.save(book)


    fun findAll(pageable: Pageable): Page<BookModel> =
        bookRepository.findAll(pageable)


    fun findAtives(pageable: Pageable): List<BookModel> =
        bookRepository.findByStatus(BookStatus.ATIVO, pageable)

    fun findById(id: Int): BookModel {
        return bookRepository.findById(id).orElseThrow()
    }

    fun delete(id: Int) {
        val book = bookRepository.findById(id).get()

        book.status = BookStatus.CANCELADO

        update(book)
    }

    fun update(book: BookModel) {
        bookRepository.save(book)
    }

    fun deleteByCostumer(costumer: CostumerModel) {
        val books = bookRepository.findByCostumerId(costumer)

        for (book in books){
            book.status = BookStatus.DELETADO
        }

        bookRepository.saveAll(books)
    }


}
