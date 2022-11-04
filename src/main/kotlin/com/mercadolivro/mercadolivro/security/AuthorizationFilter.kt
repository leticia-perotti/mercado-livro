package com.mercadolivro.mercadolivro.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

class AuthorizationFilter(
    authenticationManager: AuthenticationManager
): BasicAuthenticationFilter(authenticationManager) {
}