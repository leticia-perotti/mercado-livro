package com.mercadolivro.mercadolivro.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.mercadolivro.mercadolivro.controller.request.LoginRequest
import com.mercadolivro.mercadolivro.enum.Errors
import com.mercadolivro.mercadolivro.exception.AuthenticationException
import com.mercadolivro.mercadolivro.repository.CostumerRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val costumerRepository: CostumerRepository,
    private val jwtUtil: JwtUtil
): UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Authentication {

        try{
            val loginRequest = ObjectMapper().readValue(request.inputStream, LoginRequest::class.java)

            val id = costumerRepository.findByEmail(loginRequest.email)?.id

            val authToken = UsernamePasswordAuthenticationToken(id, loginRequest.password)

            return authenticationManager.authenticate(authToken)
        }catch (ex: Exception){
            throw AuthenticationException(Errors.ML000.message, Errors.ML000.code)

        }

    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val id = (authResult.principal as UserCostumerDetails).id

        val token = jwtUtil.generateToken(id)

        response.addHeader("Authorization", "Bearer $token")
    }

}