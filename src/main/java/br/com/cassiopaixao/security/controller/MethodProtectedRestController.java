package br.com.cassiopaixao.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 * @author CassioPaixao
 *
 */

@RestController
@RequestMapping("protected")
public class MethodProtectedRestController {

    /**
     * Este é um exemplo de alguns tipos diferentes de restrição granular
     * para os pontos finais. Você pode usar as expressões SPEL internas 
     * em @PreAuthorize, como 'hasRole ()' para determinar se um usuário tem acesso.
     * Lembre-se de que a expressão hasRole assume um prefixo 'ROLE_' em todos os nomes 
     * de função. Então 'ADMIN'
     * aqui é armazenado como 'ROLE_ADMIN' no banco de dados!
     **/
    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getProtectedGreeting() {
        return ResponseEntity.ok("Opa método protegido por admin!");
    }

}