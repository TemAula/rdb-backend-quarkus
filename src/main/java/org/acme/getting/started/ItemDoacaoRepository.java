package org.acme.getting.started;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemDoacaoRepository implements PanacheRepository<ItemDoacao> {

}
