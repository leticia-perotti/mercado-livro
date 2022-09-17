package com.mercadolivro.mercadolivro.controller

import com.mercadolivro.mercadolivro.controller.request.PostCostumerRequest
import com.mercadolivro.mercadolivro.model.CostumerModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("customer")
class CustumerController {

    @GetMapping
    fun getCostumer(): CostumerModel {
        return CostumerModel(
            "1", "Maria", "marios@armario"
        )
    }

    @PostMapping
    fun crateCostumer(
        @RequestBody custumer: PostCostumerRequest
    ) {
        println(custumer)
    }


}