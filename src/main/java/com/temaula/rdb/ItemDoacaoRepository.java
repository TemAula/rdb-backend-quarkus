package com.temaula.rdb;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemDoacaoRepository implements PanacheRepository<ItemDoacao> {

}
