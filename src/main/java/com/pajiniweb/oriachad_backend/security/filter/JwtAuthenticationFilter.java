package com.pajiniweb.oriachad_backend.security.filter;

import com.pajiniweb.oriachad_backend.security.domain.constans.ConstansStrings;
import com.pajiniweb.oriachad_backend.security.services.JwtService;
import com.pajiniweb.oriachad_backend.security.services.LogInServices;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    LogInServices userDetailsServiceImpl;
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader(ConstansStrings.HEADER_STRING);

            if (authHeader != null && authHeader.startsWith(ConstansStrings.TOKEN_PREFIX)) {
                String token = authHeader.substring(ConstansStrings.TOKEN_PREFIX.length());
                String email = jwtService.extractEmail(token);
                String role = jwtService.extractRole(token);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = loadUserDetailsByRole(email, role);

                    if (userDetails != null && jwtService.validateToken(token, userDetails)) {
                        setAuthenticationForUser(userDetails, request);
                    }
                }
            }
        } catch (ExpiredJwtException ex) {
            resolver.resolveException(request, response, null, ex);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Loads UserDetails based on the user's role.
     *
     * @param email the email of the user
     * @param role  the role of the user
     * @return UserDetails object or null if role is not supported
     */
    private UserDetails loadUserDetailsByRole(String email, String role) {
        if (Objects.equals(role, "User")) {
            return userDetailsServiceImpl.loadUserByUsername(email);
        } else if (Objects.equals(role, "Admin")) {
            return userDetailsServiceImpl.loadAdminByUsername(email);
        }
        return null;
    }

    /**
     * Sets the authentication for the current user in the SecurityContext.
     *
     * @param userDetails the UserDetails of the authenticated user
     * @param request     the current HTTP request
     */
    private void setAuthenticationForUser(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}