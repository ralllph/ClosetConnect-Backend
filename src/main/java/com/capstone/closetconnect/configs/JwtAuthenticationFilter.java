package com.capstone.closetconnect.configs;

import com.capstone.closetconnect.exceptions.LoginFailedException;
import com.capstone.closetconnect.services.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor  //creates constructor for final fields
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull  HttpServletResponse response,
            @NonNull  FilterChain filterChain) throws ServletException, IOException {

        //check for existing jwt token
        //jwt token a.ka bearer token must be passed through headers so check headers for
        // Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        //value for Authorization header key must start  Bearer with space
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            //do not execute the rest of code
            return;
        }

        //token exists, now extract from header.. substring is zero based indexed
        jwt = authHeader.substring(7);
        //now extract user email from token.. email is our username
        //to manipulate tokens(extract username and other things ), you need to add
        // jjwt-api,impl and jackson dependency
        userEmail = jwtService.extractUsername(jwt);

        //check if email exist and if user isn't already authenticated ie. don't repeat checks for authenticated user
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //check user exist in db
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            //if user exist and token valid update security context
            if(jwtService.isTokenValid(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                //update security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }else{
                throw new LoginFailedException();
            }
        }

        //pass to the next filter!to be executed
        filterChain.doFilter(request,response);
    }
}
