package com.mercadolivro.mercadolivro.controller

import com.mercadolivro.mercadolivro.controller.request.PostCostumerRequest
import com.mercadolivro.mercadolivro.controller.request.PutCostumerRequest
import com.mercadolivro.mercadolivro.model.CostumerModel
import com.mercadolivro.mercadolivro.service.CostumerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("customer")
class CustumerController(
    private val costumerService: CostumerService
    ) {

    @GetMapping
    fun getAll(
        @RequestParam name: String?
    ): List<CostumerModel> {
        costumerService.getAll(name)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun crateCostumer(
        @RequestBody custumer: PostCostumerRequest
    ) {
        val id = if(costumers.isEmpty()) 1 else costumers.last().id.toInt() + 1
        costumers.add(CostumerModel(
            id = id.toString(),
            name = custumer.name,
            email = custumer.email
        ))
    }

    @GetMapping("/{id}")
    fun getCostumer(
        @PathVariable id: String
    ): CostumerModel{
        return costumers.filter { it.id == id }.first()
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun putCostumer(
        @PathVariable id: String,
        @RequestBody costumer: PutCostumerRequest
    ){
        costumers.filter { it.id == id }.first().let {
            it.name  = costumer.name
            it.email = costumer.email
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCostumer(
        @PathVariable id: String
    ){
        costumers.removeIf{it.id == id}
    }

}