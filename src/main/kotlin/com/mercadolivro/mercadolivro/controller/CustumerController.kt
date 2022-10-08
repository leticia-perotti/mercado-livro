package com.mercadolivro.mercadolivro.controller

import com.mercadolivro.mercadolivro.controller.request.PostCostumerRequest
import com.mercadolivro.mercadolivro.controller.request.PutCostumerRequest
import com.mercadolivro.mercadolivro.extension.toCostumerModel
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
        return costumerService.getAll(name)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun crateCostumer(
        @RequestBody costumer: PostCostumerRequest
    ) {
        costumerService.create(costumer.toCostumerModel())
    }

    @GetMapping("/{id}")
    fun getCostumer(
        @PathVariable id: Int
    ): CostumerModel{
        return costumerService.returnById(id)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun putCostumer(
        @PathVariable id: Int,
        @RequestBody costumer: PutCostumerRequest
    ){
        val costumerSaved = costumerService.returnById(id)
        costumerService.editCostumer(costumer.toCostumerModel(costumerSaved))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCostumer(
        @PathVariable id: Int
    ){
        costumerService.deleteCostumer(id)
    }

}