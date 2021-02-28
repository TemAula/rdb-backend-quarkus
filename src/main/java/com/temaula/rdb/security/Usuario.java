package com.temaula.rdb.security;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Entity(name = "usuario")
public class Usuario extends PanacheEntity {

    @Column(length = 32, nullable = false)
    public String username;

    @Column(length = 32, nullable = false)
    public String password;

    @Column(length = 32, nullable = false)
    public String role;


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
