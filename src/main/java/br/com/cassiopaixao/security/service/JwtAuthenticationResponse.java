package br.com.cassiopaixao.security.service;

import java.io.Serializable;

/**
 * 
 * @author CassioPaixao
 *
 */
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
