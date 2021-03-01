package com.temaula.rdb.security;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;


@Singleton
public class UsuarioStartup {

    private static final Logger LOGGER = Logger.getLogger(UsuarioStartup.class);

    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        LOGGER.info("Iniciando o carregamento dos usuarios");
        if (Usuario.count() == 0) {
            LOGGER.info("Nao existe usuarios no banco de dados, iniciando usuarios padroes");
            Usuario.add("admin", "admin", "admin");
            Usuario.add("user", "user", "user");
        }
        LOGGER.info("Carregamento de usuarios carregado com sucesso");
    }
}