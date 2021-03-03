package com.temaula.rdb.security;


import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Optional;

@ApplicationScoped
public class UsuarioService {

    @Transactional
    public UsuarioDTO criar(UsuarioDTO dto) {
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
}
