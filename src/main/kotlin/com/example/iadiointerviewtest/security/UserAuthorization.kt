package com.example.iadiointerviewtest.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RegexRequestMatcher

@Configuration
@EnableWebSecurity
class HTTPSecurityConfiguration {

    /**
     * Establish memory based user authorization with roles.
     * The roles in question are named "guest" and "admin"
     * @return UserDetailsService
     */

    @Bean
    fun userDetailsServiceHandler(): UserDetailsService {
        val users = User.withDefaultPasswordEncoder()

        val guest = users
                .username("guest")
                .password("superMegaGuestPassword123@")
                .roles("GUEST")
                .build()

        val admin = users
                .username("admin")
                .password("UltimateUberAdminPassword123@")
                .roles("GUEST", "ADMIN")
                .build()

        return InMemoryUserDetailsManager(guest, admin)
    }

    /**
     * Establish the security constraints for roles regarding the API endpoints .
     * @param http Security object for establishing authorization constraints.
     * @return SecurityFilterChain
     */

    @Bean
    fun apiFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()

        val personIdentifierMatcher = AntPathRequestMatcher("/searchPersons/**", "GET")
        val personArgumentsMatcher = AntPathRequestMatcher("/persons/**")
        val personsMatcher = RegexRequestMatcher.regexMatcher("/persons")

        http.authorizeHttpRequests()
            .requestMatchers(personIdentifierMatcher)
            .hasAnyRole("GUEST", "ADMIN")
            .requestMatchers(personArgumentsMatcher)
            .hasRole("ADMIN")
            .requestMatchers(personsMatcher)
            .hasRole("ADMIN")
            .and()
            .httpBasic()

        return http.build()
    }
}

