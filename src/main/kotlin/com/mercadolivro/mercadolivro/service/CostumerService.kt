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
        val id = if(costumers.isEmpty()) 1 else costumers.last().id + 1
        costumers.add(CostumerModel(
            id = id.toString(),
            name = costumer.name,
            email = costumer.email
        ))
    }

    fun returnById(id: String): CostumerModel {
        return costumers.filter { it.id == id }.first()
    }

    fun editCostumer(id: String, costumer: PutCostumerRequest) {
        costumers.filter { it.id == id }.first().let {
            it.name  = costumer.name
            it.email = costumer.email
        }
    }

    fun deleteCostumer(id: String) {
        costumers.removeIf{it.id == id}
    }


}