package br.com.cassiopaixao.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cassiopaixao.model.security.User;

/**
 * 
 * @author CassioPaixao
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
