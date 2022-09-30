package com.mercadolivro.mercadolivro.service

import com.mercadolivro.mercadolivro.controller.request.PostCostumerRequest
import com.mercadolivro.mercadolivro.controller.request.PutCostumerRequest
import com.mercadolivro.mercadolivro.model.CostumerModel
import com.mercadolivro.mercadolivro.repository.CostumerRepository
import org.springframework.stereotype.Service

@Service
class CostumerService (
    private val costumerRepository: CostumerRepository
        ){
    val costumers = mutableListOf<CostumerModel>()

    fun getAll(name: String?) : List<CostumerModel>{
        name?.let{
            return costumers.filter { it.name.contains(name, ignoreCase = true) }
        }
        return costumers
    }

    fun create(costumer: CostumerModel) {
        costumerRepository.save(costumer)
    }

    fun returnById(id: Int): CostumerModel {
        return costumerRepository.findById(id).orElseThrow()
    }

    fun editCostumer(costumer: CostumerModel) {
        costumers.filter { it.id == costumer.id }.first().let {
            it.name  = costumer.name
            it.email = costumer.email
        }
    }

    fun deleteCostumer(id: Int) {
        costumers.removeIf{it.id == id}
    }


}