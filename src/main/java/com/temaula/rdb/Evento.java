package com.temaula.rdb;

import com.temaula.rdb.constraints.CustomConstraint;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Optional;

@CustomConstraint(
        delegateTo = Evento.PeriodoValidator.class,
        message = "período inválido"
)
@Entity
public class Evento extends PanacheEntity {

    /**
     * Cria um evento persistido a partir de um {@link NovoEventoRequest}
     *
     * @param novoEventoRequest não pode ser nulo, caso contrário um {@link NullPointerException} será lançado
     *
     * @return um evento persistido
     *
     * @throws NullPointerException caso novoEventoRequest informado estiver nulo
     */
    public static Evento novoEvento(@NotNull NovoEventoRequest novoEventoRequest) {
        if (novoEventoRequest == null)
            throw new IllegalArgumentException("parâmetros informados estão inválidos. Eles não podem ser nulos");
        Evento evento = new Evento();
        evento.dataCadastro = LocalDate.now();
        evento.ativo = true;
        evento.nome = novoEventoRequest.nome;
        evento.descricao = novoEventoRequest.descricao;
        evento.dataInicio = novoEventoRequest.dataInicio;
        evento.dataFim = Optional
                .ofNullable(novoEventoRequest.dataFim)
                .orElse(evento.dataInicio);
        evento.persist();
        return evento;
    }

    /**
     * Atualiza os valores do evento a partir de um evento base
     *
     * @param evento evento base
     */
    public void atualizar(Evento evento) {
        this.ativo = evento.ativo;
        this.dataInicio = evento.dataInicio;
        this.dataFim = Optional
                .ofNullable(evento.dataFim)
                .orElse(evento.dataInicio);
        this.nome = evento.nome;
        this.descricao = evento.descricao;
    }

    @NotBlank
    public String nome;
    @Lob
    @Column(length = 400)
    @Size(max = 400)
    public String descricao;
    @NotNull
    public LocalDate dataInicio;
    @NotNull
    public LocalDate dataFim;
    @NotNull
    public LocalDate dataCadastro;

    public boolean ativo;

    /**
     * Utilize o metodo {@link Evento#novoEvento(NovoEventoRequest)} <br/> Este construtor padrão é requerido pelo JPA
     */
    public Evento() {

    }

    /**
     * Implementação de um validador custom do Bean Validation para ser utilizado no momento da persistência e também
     * para ser utilizado na entrada dos métodos endpoints dos resources;
     */
    public static class PeriodoValidator implements ConstraintValidator<CustomConstraint, Evento> {

        @Override
        public boolean isValid(Evento source, ConstraintValidatorContext context) {
            return Evento.temPeriodoValido(source.dataInicio, source.dataFim);
        }
    }


    /**
     * Valida se a dataInicio e a dataFim formam um período válido para um {@link Evento}
     *
     * @param dataInicio é requerido
     * @param dataFim    pode ser nulo
     *
     * @return true se for um período válido
     */
    public static boolean temPeriodoValido(@NotNull LocalDate dataInicio, LocalDate dataFim) {

        if (dataInicio == null
                && dataFim == null) {
            return false;
        }
        if (dataInicio == null) {
            return false;
        }
        if (dataFim == null) {
            return true;
        }

        return dataInicio.isBefore(dataFim)
                || dataInicio.isEqual(dataFim);
    }
}