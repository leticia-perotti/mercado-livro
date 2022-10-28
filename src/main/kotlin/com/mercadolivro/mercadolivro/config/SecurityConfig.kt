package com.mercadolivro.mercadolivro.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration

class SecurityConfig {
    @Bean
    fun BCryptPasswordEncoder(): BCryptPasswordEncoder{
        return org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder()
    }
}