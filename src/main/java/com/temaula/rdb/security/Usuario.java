package com.temaula.rdb.security;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@UserDefinition
public class Usuario extends PanacheEntity {

    @Column(length = 32, nullable = false)
    @Username
    public String username;

    @Column(nullable = false)
    @Password
    public String password;

    @Column(length = 32, nullable = false)
    @Roles
    public String role;

    public static void add(String username, String password, String role) {
        Usuario user = new Usuario();
        user.username = username;
        user.password = BcryptUtil.bcryptHash(password);
        user.role = role;
        user.persist();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
