package cat.itb.m13.toysandsahre.seguretat.jwt;

import cat.itb.m13.toysandsahre.model.entitats.Users;
import cat.itb.m13.toysandsahre.model.serveis.ElMeuUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Log
@Component
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider tokenProvider;
    private final ElMeuUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        try {
            String token = getJwtFromRequest(req);
            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                Long userId = tokenProvider.getUserIdFromJWT(token);
                Users user = (Users) userDetailsService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
                        user.getRol(), user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetails(req));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            log.info("No se ha podido establecer la autenticación de usuario en el contexto de seguridad");
        }
        chain.doFilter(req, res);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtProvider.TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProvider.TOKEN_PREFIX)) {
            return bearerToken.substring(JwtProvider.TOKEN_PREFIX.length(), bearerToken.length());
        }
        return null;
    }
}
