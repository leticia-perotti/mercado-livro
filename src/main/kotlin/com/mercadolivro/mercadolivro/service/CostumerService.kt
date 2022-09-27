package com.mercadolivro.mercadolivro.service

import com.mercadolivro.mercadolivro.model.CostumerModel
import org.springframework.stereotype.Service

@Service
class CostumerService {
    fun getAll(name: String?) : List<CostumerModel>{
        name?.let{
            return costumers.filter { it.name.contains(name, ignoreCase = true) }
        }
        return costumers
    }

    val costumers = mutableListOf<CostumerModel>()



}