package com.project.MovieWebsite.filters;

import com.project.MovieWebsite.components.JwtTokenUtil;

import com.project.MovieWebsite.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor

public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${api.prefix}")
    private String apiPrefix;

    private final UserDetailsService userDetailsService;

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
           if(isByPassToken(request)){
                filterChain.doFilter(request, response);
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }
            if(authHeader != null && authHeader.startsWith("Bearer ")){
                final String token = authHeader.substring(7);
                final String email = jwtTokenUtil.extractEmail(token);
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    User userDetails = (User) userDetailsService.loadUserByUsername(email);
                    if(jwtTokenUtil.validationToken(token, userDetails)){
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails((request)));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
            filterChain.doFilter(request, response);
        }
        catch (Exception e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }

    }

    private boolean isByPassToken(@NotNull HttpServletRequest request){
        final List<Pair<String, String>> byPassTokens = Arrays.asList(
                Pair.of(String.format("%s/roles", apiPrefix), "GET"),
                Pair.of(String.format("%s/users/login", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/login_gg", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/register", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/forgot-password", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/reset-password", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/check-otp", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/images", apiPrefix), "GET"),
                Pair.of(String.format("%s/movies", apiPrefix), "GET"),
                Pair.of(String.format("%s/genres", apiPrefix), "GET"),
                Pair.of(String.format("%s/countries", apiPrefix), "GET"),
                Pair.of(String.format("%s/movie_types", apiPrefix), "GET"),
                Pair.of(String.format("%s/episodes", apiPrefix), "GET"),
                Pair.of(String.format("%s/users/authenticate-account", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/check-register", apiPrefix), "POST"),
                Pair.of(String.format("%s/movie_views", apiPrefix), "POST"),
                Pair.of(String.format("%s/movie_views/update_view_day", apiPrefix), "GET"),
                Pair.of(String.format("%s/movie_views/top_view_day", apiPrefix), "GET"),
                Pair.of(String.format("%s/movie_views/top_view_week", apiPrefix), "GET"),
                Pair.of(String.format("%s/movie_views/top_view_month", apiPrefix), "GET"),
                Pair.of(String.format("%s/rates/information_rate", apiPrefix), "POST"),
                Pair.of(String.format("%s/ads/check_trading_code", apiPrefix), "POST"),
                Pair.of(String.format("%s/payments/create_order_ads", apiPrefix), "POST"),
                Pair.of(String.format("%s/ads/update_ads_payment", apiPrefix), "PUT"),
                Pair.of(String.format("%s/ads/get_ads", apiPrefix), "GET"),
                Pair.of(String.format("%s/ads/images", apiPrefix), "GET"),
                Pair.of(String.format("%s/comments", apiPrefix), "GET")
        );

        for (Pair<String, String> byPassToken: byPassTokens){
            if(request.getServletPath().contains(byPassToken.getFirst())
                    && request.getMethod().equals(byPassToken.getSecond())){
                return true;
            }
        }
        return false;
    }
}