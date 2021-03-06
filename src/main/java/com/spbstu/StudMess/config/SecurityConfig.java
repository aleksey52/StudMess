package com.spbstu.StudMess.config;

import com.spbstu.StudMess.model.enums.Role;
import com.spbstu.StudMess.security.CustomAccessDeniedHandler;
import com.spbstu.StudMess.security.CustomAuthenticationEntryPoint;
import com.spbstu.StudMess.security.JwtTokenAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String AUTH_URL = "/v1/auth/**";
    private static final String[] DOMAIN_URLS = {
            "/v1/users/**",
            "/v1/chats/**",
            "/v1/groups/**",
            "/v1/schedule/**",
            "/v1/subjects/**",
            "/v1/tasks/**",
            "/v1/teachers/**"
    };
    private static final String[] OPEN_API_URLS = {
            "/api-docs/**",
            "/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/actuator/**",
            "/management/**"
    };
    private final JwtTokenAuthorizationFilter jwtTokenAuthorizationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers(OPEN_API_URLS).permitAll()
                .antMatchers(AUTH_URL).permitAll()
                .antMatchers(DOMAIN_URLS).hasAuthority(Role.USER.toString())
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtTokenAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CustomAccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CustomAuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // Used by spring security if CORS is enabled.
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
