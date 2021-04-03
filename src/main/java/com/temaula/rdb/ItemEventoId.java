package com.temaula.rdb;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ItemEventoId implements Serializable {

    public static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "evento_id", updatable = false)
    public Evento evento;

    @ManyToOne
    @JoinColumn(name = "item_id", updatable = false)
    public Item item;

    public static ItemEventoId of(Evento evento, Item item) {
        ItemEventoId id = new ItemEventoId();
        id.evento = evento;
        id.item = item;
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEventoId that = (ItemEventoId) o;
        return Objects.equals(evento, that.evento) && Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(evento, item);
    }
}
