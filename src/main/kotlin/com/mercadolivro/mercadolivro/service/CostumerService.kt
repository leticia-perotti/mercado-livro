package com.mercadolivro.mercadolivro.service

import com.mercadolivro.mercadolivro.enum.CostumerStatus
import com.mercadolivro.mercadolivro.enum.Errors
import com.mercadolivro.mercadolivro.enum.Role
import com.mercadolivro.mercadolivro.exception.NotFoundException
import com.mercadolivro.mercadolivro.model.CostumerModel
import com.mercadolivro.mercadolivro.repository.CostumerRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class CostumerService (
    private val costumerRepository: CostumerRepository,
    private val bookService: BookService,
    private val bCrypt: BCryptPasswordEncoder
        ){

    fun getAll(name: String?) : List<CostumerModel>{
        name?.let{
            return costumerRepository.findByNameContaining(name)
        }
        return costumerRepository.findAll().toList()
    }

    fun create(costumer: CostumerModel) {
        val costumerCopy = costumer.copy(
            roles = setOf(Role.COSTUMER),
            password = bCrypt.encode(costumer.password)
        )
        costumerRepository.save(costumerCopy)
    }

    fun returnById(id: Int): CostumerModel {
        return costumerRepository.findById(id).orElseThrow{
            NotFoundException(Errors.ML201.message.format(id), Errors.ML201.code)
        }
    }

    fun editCostumer(costumer: CostumerModel) {
        if (!costumerRepository.existsById(costumer.id!!)){
            throw NotFoundException(Errors.ML201.message.format(costumer.id), Errors.ML201.code)
        }
        costumerRepository.save(costumer)
    }

    fun deleteCostumer(id: Int) {
        val costumer = returnById(id)
        bookService.deleteByCostumer(costumer)
        costumer.status = CostumerStatus.INATIVO
        costumerRepository.save(costumer)
    }

    fun emailAvailable(value: String): Boolean {
        return !costumerRepository.existsByEmail(value)
    }


}