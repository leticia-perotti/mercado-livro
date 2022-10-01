package com.mercadolivro.mercadolivro.repository

import com.mercadolivro.mercadolivro.model.CostumerModel
import org.springframework.data.repository.CrudRepository

interface CostumerRepository: CrudRepository<CostumerModel, Int> {

    fun findByNameContaining(name : String): List<CostumerModel>
}