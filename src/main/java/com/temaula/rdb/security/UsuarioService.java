package com.temaula.rdb.security;


import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class UsuarioService {

    @Transactional
    public UsuarioDTO criar(UsuarioDTO dto) {
        Usuario usuario = dto.toUsuario();
        usuario.role = Roles.USER.get();
        usuario.persist();
        return UsuarioDTO.of(usuario);
    }
}
