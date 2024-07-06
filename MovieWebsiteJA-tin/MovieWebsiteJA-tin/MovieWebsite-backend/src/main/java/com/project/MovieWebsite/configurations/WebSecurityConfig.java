
package com.project.MovieWebsite.configurations;

import com.project.MovieWebsite.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableWebMvc

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
                                    String.format("%s/users/forgot-password", apiPrefix),
                                    String.format("%s/users/reset-password", apiPrefix),
                                    String.format("%s/users/authenticate-account", apiPrefix),
                                    String.format("%s/users/check-otp", apiPrefix),
                                    String.format("%s/users/check-register", apiPrefix),
                                    String.format("%s/episodes/**", apiPrefix),
                                    String.format("%s/movie_views/top_view_day", apiPrefix),
                                    String.format("%s/movie_views/top_view_week", apiPrefix),
                                    String.format("%s/movie_views/top_view_month", apiPrefix),
                                    String.format("%s/ads/check_trading_code", apiPrefix),
                                    String.format("%s/payments/create_order_ads/**", apiPrefix),
                                    String.format("%s/ads/update_ads_payment", apiPrefix),
                                    String.format("%s/comments/**", apiPrefix),
                                    String.format("%s/users/login_gg", apiPrefix),
                                    String.format("%s/ads/get_ads/**", apiPrefix),
                                    String.format("%s/ads/images/**", apiPrefix)
                            )
                            .permitAll()
                            .requestMatchers(HttpMethod.GET, String.format("%s/roles", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.GET, String.format("%s/accounts/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(GET, String.format("%s/movie_views/update_view_day", apiPrefix)).permitAll()
                            .requestMatchers(GET, String.format("%s/movie_views/last_delete_view", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(DELETE, String.format("%s/movie_views/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(POST, String.format("%s/movie_views/**", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.GET, String.format("%s/genres/**", apiPrefix)).permitAll()
                            .requestMatchers(POST, String.format("%s/movie_views", apiPrefix)).permitAll()
                            .requestMatchers(GET, String.format("%s/movies/**", apiPrefix)).permitAll()
                            .requestMatchers(GET, String.format("%s/users/images/**", apiPrefix)).permitAll()
                            .requestMatchers(POST, String.format("%s/users/details", apiPrefix)).hasAnyRole("ADMIN", "USER")
                            .requestMatchers(PUT, String.format("%s/users/details/**", apiPrefix)).hasAnyRole("USER", "ADMIN")
                            .requestMatchers(GET, String.format("%s/countries/**", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.GET, String.format("%s/movie_types/**", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.GET, String.format("%s/episodes", apiPrefix)).permitAll()
                            .requestMatchers(PUT, String.format("%s/genres/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(POST, String.format("%s/genres/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(DELETE, String.format("%s/genres/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(PUT, String.format("%s/movies/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(POST, String.format("%s/movies/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(DELETE, String.format("%s/movies/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(PUT, String.format("%s/episodes/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(POST, String.format("%s/episodes", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(DELETE, String.format("%s/episodes/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(PUT, String.format("%s/movie_types/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(POST, String.format("%s/movie_types/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(DELETE, String.format("%s/movie_types/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(PUT, String.format("%s/countries/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(POST, String.format("%s/countries/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(DELETE, String.format("%s/countries/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(POST, String.format("%s/users/upload_avatar/**", apiPrefix)).hasAnyRole("ADMIN", "USER")
                            .requestMatchers(POST, String.format("%s/users/changePassword", apiPrefix)).hasAnyRole("USER", "ADMIN")
                            .requestMatchers(POST, String.format("%s/users/checkCurrentPassword", apiPrefix)).hasAnyRole( "USER", "ADMIN")
                            .requestMatchers(POST, String.format("%s/users/vip_periods/**", apiPrefix)).hasAnyRole( "USER", "ADMIN")
                            .requestMatchers(GET, String.format("%s/users/vip_periods", apiPrefix)).hasAnyRole( "USER", "ADMIN")
                            .requestMatchers(DELETE, String.format("%s/users/vip_periods", apiPrefix)).hasAnyRole( "USER", "ADMIN")
                            .requestMatchers(POST, String.format("%s/favourites", apiPrefix)).hasAnyRole( "USER", "ADMIN")
                            .requestMatchers(GET, String.format("%s/favourites/**", apiPrefix)).hasAnyRole( "USER", "ADMIN")
                            .requestMatchers(POST, String.format("%s/favourites/**", apiPrefix)).hasAnyRole( "USER", "ADMIN")
                            .requestMatchers(POST, String.format("%s/rates", apiPrefix)).hasAnyRole( "USER", "ADMIN")
                            .requestMatchers(POST, String.format("%s/rates/information_rate", apiPrefix)).permitAll()
                            .requestMatchers(GET, String.format("%s/rates/last_delete_rate", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(DELETE, String.format("%s/rates/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(POST, String.format("%s/payments/create_order/**", apiPrefix)).hasAnyRole( "USER", "ADMIN")
                            .requestMatchers(GET, String.format("%s/payments/vnpay_payment_return/**", apiPrefix)).hasAnyRole( "USER", "ADMIN")
                            .requestMatchers(POST, String.format("%s/orders", apiPrefix)).hasAnyRole( "USER", "ADMIN")
                            .requestMatchers(GET, String.format("%s/orders/all_orders", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(GET, String.format("%s/orders/order_by_user", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(PUT, String.format("%s/ads/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(POST, String.format("%s/ads/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(DELETE, String.format("%s/ads/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(GET, String.format("%s/orders/years", apiPrefix)).hasRole("ADMIN")

                            .anyRequest()
                            .authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable);
        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            // Khi deploy lên server phải xóa vì đây là mặc định những request ko an toàn vẫn đc nhận
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                configuration.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });
        return http.build();
    }
}