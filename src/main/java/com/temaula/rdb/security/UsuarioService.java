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

        if (jaExisteUsuario(dto.username)) {
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

        if (!usuario.username.equals(dto.username) && jaExisteUsuario(dto.username)) {
            throw new IllegalArgumentException("Username já utilizado");
        }
        usuario.atualizar(dto.toUsuario(), BcryptUtil.bcryptHash(dto.password));
        usuario.persist();
        return UsuarioDTO.of(usuario);
    }

    private boolean jaExisteUsuario(String username) {
        long count = Usuario.find("username", username).count();
        return count > 0;
    }
}
