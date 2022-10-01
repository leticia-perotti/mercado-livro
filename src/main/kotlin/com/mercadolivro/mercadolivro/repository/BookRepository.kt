package com.mercadolivro.mercadolivro.repository

import com.mercadolivro.mercadolivro.model.BookModel
import org.springframework.data.repository.CrudRepository

interface BookRepository: CrudRepository<BookModel, Int> {
}