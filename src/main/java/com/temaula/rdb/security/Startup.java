package com.temaula.rdb.security;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import io.quarkus.runtime.StartupEvent;


@Singleton
public class Startup {
    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        // reset and load all test users
        Usuario.deleteAll();
        Usuario.add("admin", "admin", "admin");
        Usuario.add("user", "user", "user");
    }
}