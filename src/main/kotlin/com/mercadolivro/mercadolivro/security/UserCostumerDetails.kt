package com.mercadolivro.mercadolivro.security

import com.mercadolivro.mercadolivro.enum.CostumerStatus
import com.mercadolivro.mercadolivro.model.CostumerModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserCostumerDetails(val costumerModel: CostumerModel): UserDetails {

    val id = costumerModel.id!!
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = costumerModel.roles.map { SimpleGrantedAuthority(it.description) }.toMutableList()

    override fun getPassword(): String = costumerModel.password

    override fun getUsername(): String = costumerModel.id.toString()

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = costumerModel.status == CostumerStatus.ATIVO

}