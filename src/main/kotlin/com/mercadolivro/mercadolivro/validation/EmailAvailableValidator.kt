package com.mercadolivro.mercadolivro.validation

import com.mercadolivro.mercadolivro.service.CostumerService
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class EmailAvailableValidator(var costumerService: CostumerService): ConstraintValidator<EmailAvailable, String> {



    override fun isValid(p0: String?, p1: ConstraintValidatorContext?): Boolean {
        if(p0.isNullOrEmpty()){
            return false
        }
        return costumerService.emailAvailable(p0)
    }
}