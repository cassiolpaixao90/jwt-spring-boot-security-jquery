package br.com.cassiopaixao.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.cassiopaixao.model.security.User;
import br.com.cassiopaixao.security.JwtUserFactory;
import br.com.cassiopaixao.security.repository.UserRepository;

/**
 * Created by Cássio Paixão.
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("Não foi encontrado usuario '%s'.", username));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}
