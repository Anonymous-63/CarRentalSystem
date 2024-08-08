package com.anonymous63.crs.securities;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenHelper jwtTokenHelper;

    public JwtAuthenticationFilter(@NonNull UserDetailsService userDetailsService, @NonNull JwtTokenHelper jwtTokenHelper) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenHelper = jwtTokenHelper;
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = extractJwtToken(request);
        String username = null;

        if (jwtToken != null) {
            username = getUsernameFromToken(jwtToken);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            authenticateUser(username, jwtToken, request);
        } else if (username == null) {
            logger.warn("Username is null");
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtToken(HttpServletRequest request) {
        String requestToken = request.getHeader("Authorization");
        if (requestToken != null && requestToken.startsWith("Bearer ")) {
            return requestToken.substring(7);
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
            return null;
        }
    }

    private String getUsernameFromToken(String jwtToken) {
        try {
            return jwtTokenHelper.getUsernameFromToken(jwtToken);
        } catch (IllegalArgumentException e) {
            logger.warn("Unable to get JWT Token", e);
        } catch (ExpiredJwtException e) {
            logger.warn("JWT Token has expired", e);
        } catch (MalformedJwtException e) {
            logger.warn("JWT Token is malformed", e);
        } catch (Exception e) {
            logger.warn("JWT Token is invalid", e);
        }
        return null;
    }

    private void authenticateUser(String username, String jwtToken, HttpServletRequest request) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        if (Boolean.TRUE.equals(this.jwtTokenHelper.validateToken(jwtToken, userDetails))) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else {
            logger.warn("Token is not valid");
        }
    }

}
