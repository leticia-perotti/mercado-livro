package com.mercadolivro.mercadolivro.config

import com.mercadolivro.mercadolivro.enum.Role
import com.mercadolivro.mercadolivro.repository.CostumerRepository
import com.mercadolivro.mercadolivro.security.AuthenticationFilter
import com.mercadolivro.mercadolivro.security.AuthorizationFilter
import com.mercadolivro.mercadolivro.security.CustomAuthenticationEntryPoint
import com.mercadolivro.mercadolivro.security.JwtUtil
import com.mercadolivro.mercadolivro.service.UserDetailCostumerService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val costumerRepository: CostumerRepository,
    private val userDetails: UserDetailCostumerService,
    private val jwtUtil: JwtUtil,
    private val customEntryPoint: CustomAuthenticationEntryPoint
): WebSecurityConfigurerAdapter() {

    private val publicPostMatchers = arrayOf(
        "/customer"
    )

    private val ADMIN_MATCHERS = arrayOf(
        "/admin/**"
    )

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetails).passwordEncoder(BCryptPasswordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()
        http.authorizeHttpRequests()
            .antMatchers(HttpMethod.POST, *publicPostMatchers).permitAll()
            .antMatchers(*ADMIN_MATCHERS).hasAuthority(Role.ADMIN.description)
            .anyRequest().authenticated()
        http.addFilter(AuthenticationFilter(authenticationManager(), costumerRepository, jwtUtil))
        http.addFilter(AuthorizationFilter(authenticationManager(), jwtUtil, userDetails))
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.exceptionHandling().authenticationEntryPoint(customEntryPoint)
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("v2/api-docs", "/configuration/ui",
        "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**")
    }

    @Bean
    fun corsConfig(): CorsFilter{
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()

        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)

        return CorsFilter(source)
    }
    @Bean
    fun BCryptPasswordEncoder(): BCryptPasswordEncoder{
        return org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder()
    }
}