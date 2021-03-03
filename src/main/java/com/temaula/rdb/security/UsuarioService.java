package com.temaula.rdb.security;


import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class UsuarioService {

    @Transactional
    public void criar(Usuario usuario) {
        usuario.role = Roles.USER.get();
        usuario.persist();
    }
}
