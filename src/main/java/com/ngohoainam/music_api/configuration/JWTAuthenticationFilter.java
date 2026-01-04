package com.ngohoainam.music_api.configuration;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.MACVerifier;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Value("${spring.jwt.signerKey}")
    private String signerKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {
            JWSObject jwsObject = JWSObject.parse(token);
            boolean verified = jwsObject.verify(new MACVerifier(signerKey.getBytes(StandardCharsets.UTF_8)));

            if (!verified) {
                log.warn("Token is not verified");
                filterChain.doFilter(request, response);
                return;
            }

            Map<String, Object> payload = jwsObject.getPayload().toJSONObject();
            String email = (String) payload.get("sub");
            List<String> roles = (List<String>) payload.get("roles");
            List<String> permissions = (List<String>) payload.get("permissions");

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (roles != null)
                roles.forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                });
            if (permissions != null)
                permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(email, null, authorities);
            log.info("✅ Authenticated user: {} with authorities: {}", email, authorities);

            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }


        filterChain.doFilter(request, response);
    }
}
