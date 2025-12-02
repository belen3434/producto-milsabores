package milsabores.producto.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        final String authorizationHeader = request.getHeader("Authorization");
        
        String username = null;
        String role = null;
        String jwt = null;
        
        System.out.println("=== PRODUCTOS JWT FILTER DEBUG ===");
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Authorization Header: " + authorizationHeader);
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            System.out.println("JWT Token extraído: " + jwt.substring(0, Math.min(20, jwt.length())) + "...");
            try {
                username = jwtUtil.extractUsername(jwt);
                role = jwtUtil.extractRole(jwt);
                System.out.println("Username extraído: " + username);
                System.out.println("Role extraído: " + role);
            } catch (Exception e) {
                System.err.println("ERROR al procesar JWT: " + e.getMessage());
                e.printStackTrace();
                logger.warn("No se pudo extraer información del JWT: " + e.getMessage());
            }
        }
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("Validando token para usuario: " + username);
            try {
                if (jwtUtil.validateToken(jwt, username)) {
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(authority));
                    
                    // Debug logging
                    System.out.println("JWT Filter - Username: " + username);
                    System.out.println("JWT Filter - Role: " + role);
                    System.out.println("JWT Filter - Authority: " + authority.getAuthority());
                    
                    // Agregar userId como detalle para poder accederlo desde el controller
                    Long userId = jwtUtil.extractUserId(jwt);
                    System.out.println("JWT Filter - UserId: " + userId);
                    authToken.setDetails(userId);
                    
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("Authentication establecida exitosamente en productos");
                } else {
                    System.err.println("Token no válido para usuario: " + username);
                }
            } catch (Exception e) {
                System.err.println("ERROR en validación de token: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        System.out.println("Authentication final en productos: " + SecurityContextHolder.getContext().getAuthentication());
        System.out.println("=================================");
        
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            System.err.println("ERROR en filterChain.doFilter: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}