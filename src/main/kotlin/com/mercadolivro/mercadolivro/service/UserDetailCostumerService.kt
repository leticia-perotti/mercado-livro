package com.mercadolivro.mercadolivro.service

import com.mercadolivro.mercadolivro.exception.AuthenticationException
import com.mercadolivro.mercadolivro.repository.CostumerRepository
import com.mercadolivro.mercadolivro.security.UserCostumerDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailCostumerService(
    private val costumerRepository: CostumerRepository
): UserDetailsService {

    override fun loadUserByUsername(id: String): UserDetails {
        val costumer = costumerRepository.findById(id.toInt()).orElseThrow{
            AuthenticationException("Usuario não encontrado", "999")
        }

        return UserCostumerDetails(costumer)
    }

}