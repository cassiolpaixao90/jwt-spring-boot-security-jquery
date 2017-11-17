package br.com.cassiopaixao.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 
 * @author CassioPaixao
 *
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
    	// Isso é invocado quando o usuário tenta acessar um recurso REST seguro sem fornecer quaisquer credenciais
        // Devemos simplesmente enviar uma resposta não autorizada 401 porque não há "página de login" para redirecionar para
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}