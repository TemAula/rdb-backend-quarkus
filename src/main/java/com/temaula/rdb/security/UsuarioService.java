package com.temaula.rdb.security;


import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
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

    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioDTO dto, Principal principal) {
        Usuario usuario = Usuario
                .<Usuario>findByIdOptional(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado com o Id " + id));
        usuario.atualizar(dto.toUsuario(), BcryptUtil.bcryptHash(dto.password));
        usuario.persist();
        return UsuarioDTO.of(usuario);
    }
}
