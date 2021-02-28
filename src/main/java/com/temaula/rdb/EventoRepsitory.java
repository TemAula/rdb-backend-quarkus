package com.temaula.rdb;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class EventoRepsitory implements PanacheRepository<Evento>{

}
