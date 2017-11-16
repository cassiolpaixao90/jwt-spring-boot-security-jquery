package br.com.cassiopaixao.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 
 * @author CassioPaixao
 *
 */

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.header}")
	private String tokenHeader;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		final String requestHeader = request.getHeader(this.tokenHeader);

		String username = null;
		String authToken = null;
		if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
			authToken = requestHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(authToken);
			} catch (IllegalArgumentException e) {
				logger.error("Ocorreu um erro ao obter o nome de usuário do token", e);
			} catch (ExpiredJwtException e) {
				logger.warn("o token expirou e já não é válido", e);
			}
		} else {
			logger.warn("não encontrou cadeia de suporte, ignorará o cabeçalho");
		}

		logger.info("checando autenticação para o usuario " + username);
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			// Não é necessário carregar os detalhes de uso do banco de dados. Você também
			// pode armazenar a informação
			// no token e lê-lo dele. Você decide ;)
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			// Para validação simples é completamente suficiente verificar apenas a
			// integridade do token. Você não precisa chamar
			// o banco de dados obrigatório. Novamente cabe a você;)
			if (jwtTokenUtil.validateToken(authToken, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				logger.info("usuario autenticado " + username + ", configuracao security context");
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(request, response);
	}
}