package com.mercadolivro.mercadolivro.repository

import com.mercadolivro.mercadolivro.enum.BookStatus
import com.mercadolivro.mercadolivro.model.BookModel
import com.mercadolivro.mercadolivro.model.CostumerModel
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository


interface BookRepository: JpaRepository<BookModel, Int> {
    fun findByStatus(status: BookStatus, pageable: Pageable): List<BookModel>

    fun findByCostumerId(costumer: CostumerModel): List<BookModel>

    fun findByStatus(status: BookStatus): List<BookModel>
}