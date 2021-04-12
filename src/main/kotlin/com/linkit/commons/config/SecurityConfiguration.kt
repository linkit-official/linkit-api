package com.linkit.commons.config

import com.linkit.commons.security.TokenAuthenticationFilter
import com.linkit.commons.security.TokenAuthenticationProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher

@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
@EnableWebSecurity
@Configuration
class SecurityConfiguration(
    private val provider: TokenAuthenticationProvider
) : WebSecurityConfigurerAdapter() {

    override fun configure(web: WebSecurity) {
        web.ignoring().mvcMatchers(HttpMethod.OPTIONS, "/**")
        web.ignoring().antMatchers(
            "/v2/api-docs", "/swagger-resources/**",
            "/swagger-ui.html", "/webjars/**", "/swagger/**"
        )
    }

    override fun configure(http: HttpSecurity) {
        http
            .httpBasic().disable() // 로그인 폼 안띄우기
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT token 으로 사용
            .and()
            .authenticationProvider(provider)
            .addFilterBefore(restAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    fun restAuthenticationFilter(): TokenAuthenticationFilter {
        val filter = TokenAuthenticationFilter(
            OrRequestMatcher(
                AntPathRequestMatcher("/**")
            )
        )
        filter.setAuthenticationManager(authenticationManager())
        filter.setAuthenticationSuccessHandler(successHandler())
        return filter
    }

    @Bean
    fun successHandler(): SimpleUrlAuthenticationSuccessHandler {
        return SimpleUrlAuthenticationSuccessHandler()
    }
}
