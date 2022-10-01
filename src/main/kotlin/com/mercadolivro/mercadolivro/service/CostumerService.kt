package com.mercadolivro.mercadolivro.service

import com.mercadolivro.mercadolivro.model.CostumerModel
import com.mercadolivro.mercadolivro.repository.CostumerRepository
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class CostumerService (
    private val costumerRepository: CostumerRepository
        ){

    fun getAll(name: String?) : List<CostumerModel>{
        name?.let{
            return costumerRepository.findByNameContaining(name)
        }
        return costumerRepository.findAll().toList()
    }

    fun create(costumer: CostumerModel) {
        costumerRepository.save(costumer)
    }

    fun returnById(id: Int): CostumerModel {
        return costumerRepository.findById(id).orElseThrow()
    }

    fun editCostumer(costumer: CostumerModel) {
        if (!costumerRepository.existsById(costumer.id!!)){
            throw Exception()
        }
        costumerRepository.save(costumer)
    }

    fun deleteCostumer(id: Int) {
        if (!costumerRepository.existsById(id)){
            throw Exception()
        }
        costumerRepository.deleteById(id)
    }


}