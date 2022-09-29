package com.mercadolivro.mercadolivro.service

import com.mercadolivro.mercadolivro.controller.request.PostCostumerRequest
import com.mercadolivro.mercadolivro.controller.request.PutCostumerRequest
import com.mercadolivro.mercadolivro.model.CostumerModel
import org.springframework.stereotype.Service

@Service
class CostumerService {
    val costumers = mutableListOf<CostumerModel>()

    fun getAll(name: String?) : List<CostumerModel>{
        name?.let{
            return costumers.filter { it.name.contains(name, ignoreCase = true) }
        }
        return costumers
    }

    fun create(costumer: CostumerModel) {
        costumer.id = if(costumers.isEmpty()) 1.toString() else costumers.last().id!! + 1
        costumers.add(costumer)
    }

    fun returnById(id: String): CostumerModel {
        return costumers.filter { it.id == id }.first()
    }

    fun editCostumer(costumer: CostumerModel) {
        costumers.filter { it.id == costumer.id }.first().let {
            it.name  = costumer.name
            it.email = costumer.email
        }
    }

    fun deleteCostumer(id: String) {
        costumers.removeIf{it.id == id}
    }


}