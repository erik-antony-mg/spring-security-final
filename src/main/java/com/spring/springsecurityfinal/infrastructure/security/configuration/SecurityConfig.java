package com.spring.springsecurityfinal.infrastructure.security.configuration;


import com.spring.springsecurityfinal.domain.enums.RoleName;
import com.spring.springsecurityfinal.infrastructure.security.filters.JwtAuthenticationFilter;
import com.spring.springsecurityfinal.infrastructure.security.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint entryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(request ->{request
                        .requestMatchers(HttpMethod.POST,"/auth/login")
                            .permitAll()
                        .requestMatchers(HttpMethod.GET,"/auth/profile")
                            .permitAll()
                        .requestMatchers(HttpMethod.GET,"/usuarios")
                        .hasAnyRole(RoleName.ADMINISTRADOR.name(),RoleName.ASISTENTE.name(),RoleName.VISITANTE.name())
                        .requestMatchers(HttpMethod.POST,"/usuarios")
//                            .hasRole(RoleName.ADMINISTRADOR.name())
                        .permitAll()
                        .requestMatchers(HttpMethod.GET,"/usuarios/{usuarioId}")
                            .hasAnyRole(RoleName.ADMINISTRADOR.name(),RoleName.ASISTENTE.name())

                        .anyRequest().authenticated();
                })
                .exceptionHandling(exception->{exception
                        .authenticationEntryPoint(entryPoint)
                        .accessDeniedHandler(accessDeniedHandler);

                })
                .build();

    }
}
