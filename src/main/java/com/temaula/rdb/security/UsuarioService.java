package com.temaula.rdb.security;


import io.quarkus.elytron.security.common.BcryptUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Optional;

@ApplicationScoped
public class UsuarioService {

    @Transactional
    public UsuarioDTO criar(UsuarioDTO dto) {

        long count = Usuario.find("username", dto.username).count();
        if (count > 0) {
            throw new IllegalArgumentException("Username já utilizado");
        }
        Usuario usuario = dto.toUsuario();
        usuario.role = Roles.USER.get();
        usuario.password = BcryptUtil.bcryptHash(dto.password);
        usuario.persist();
        return UsuarioDTO.of(usuario);
    }

    public Optional<UsuarioDTO> me(Principal principal) {
        Optional<Usuario> usuario = Usuario.find("username", principal.getName()).singleResultOptional();
        return usuario.map(UsuarioDTO::of);
    }

    public UsuarioDTO atualizar(Long id, UsuarioDTO usuario, Principal principal) {
        return null;
    }
}
