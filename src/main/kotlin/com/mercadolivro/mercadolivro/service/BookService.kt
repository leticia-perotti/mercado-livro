package com.mercadolivro.mercadolivro.service

import com.mercadolivro.mercadolivro.enum.BookStatus
import com.mercadolivro.mercadolivro.model.BookModel
import com.mercadolivro.mercadolivro.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {
    fun create(book: BookModel) {
        bookRepository.save(book)
    }

    fun findAll(): List<BookModel> {
        return bookRepository.findAll().toList()
    }

    fun findAtives(): List<BookModel> {
        return bookRepository.findByStatus(BookStatus.ATIVO)
    }

}
