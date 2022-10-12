package com.mercadolivro.mercadolivro.exception

import com.mercadolivro.mercadolivro.controller.response.ErrorResponse
import com.mercadolivro.mercadolivro.controller.response.FieldErrorResponse
import com.mercadolivro.mercadolivro.enum.Errors
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.Exception
import java.net.http.HttpRequest

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException, request: HttpRequest): ResponseEntity<ErrorResponse>{
        val erro = ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.message,
            ex.errorCode,
            null
        )
        return ResponseEntity(erro, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException, request: HttpRequest): ResponseEntity<ErrorResponse>{
        val erro = ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.message,
            ex.errorCode,
            null
        )
        return ResponseEntity(erro, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException, request: HttpRequest): ResponseEntity<ErrorResponse>{
        val erro = ErrorResponse(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            Errors.ML001.message,
            Errors.ML001.code,
            ex.bindingResult.fieldErrors.map{
                FieldErrorResponse(it.defaultMessage?: "Invalid", it.field)
            }
        )
        return ResponseEntity(erro, HttpStatus.UNPROCESSABLE_ENTITY)
    }
}