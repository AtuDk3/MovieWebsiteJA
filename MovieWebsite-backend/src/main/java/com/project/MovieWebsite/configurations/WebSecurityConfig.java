package com.project.MovieWebsite.configurations;

import com.project.MovieWebsite.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class WebSecurityConfig {

    @Value("${api.prefix}")
    private String apiPrefix;

    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests ->{
                    requests.requestMatchers(
                            String.format("%s/users/register", apiPrefix),
                            String.format("%s/users/login", apiPrefix),
                            String.format("%s/genres", apiPrefix),
                            String.format("%s/movies", apiPrefix),
                            String.format("%s/country", apiPrefix)
                    )
                    .permitAll()
                    .requestMatchers(HttpMethod.PUT, String.format("%s/genres/**/**", apiPrefix)).hasRole("Admin")
                    .requestMatchers(HttpMethod.POST, String.format("%s/genres/**", apiPrefix)).hasRole("Admin")
                    .requestMatchers(HttpMethod.DELETE, String.format("%s/genres/**", apiPrefix)).hasRole("Admin")
                    .requestMatchers(HttpMethod.PUT, String.format("%s/movies/**", apiPrefix)).hasRole("Admin")
                    .requestMatchers(HttpMethod.POST, String.format("%s/movies/**", apiPrefix)).hasRole("Admin")
                    .requestMatchers(HttpMethod.DELETE, String.format("%s/movies/**", apiPrefix)).hasRole("Admin")
                    .requestMatchers(HttpMethod.PUT, String.format("%s/episodes/**", apiPrefix)).hasRole("Admin")
                    .requestMatchers(HttpMethod.POST, String.format("%s/episodes/**", apiPrefix)).hasRole("Admin")
                    .requestMatchers(HttpMethod.DELETE, String.format("%s/episodes/**", apiPrefix)).hasRole("Admin")
                    .requestMatchers(HttpMethod.PUT, String.format("%s/movie_types/**", apiPrefix)).hasRole("Admin")
                    .requestMatchers(HttpMethod.POST, String.format("%s/movie_types/**", apiPrefix)).hasRole("Admin")
                    .requestMatchers(HttpMethod.DELETE, String.format("%s/movie_types/**", apiPrefix)).hasRole("Admin")
                    .requestMatchers(HttpMethod.PUT, String.format("%s/countries/**", apiPrefix)).hasRole("Admin")
                    .requestMatchers(HttpMethod.POST, String.format("%s/countries/**", apiPrefix)).hasRole("Admin")
                    .requestMatchers(HttpMethod.DELETE, String.format("%s/countries/**", apiPrefix)).hasRole("Admin")
                    .anyRequest()
                    .authenticated();
                });
        return http.build();
    }


}
