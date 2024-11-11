package it.powercolle.dto;

import io.quarkus.hibernate.orm.panache.common.NestedProjectedClass;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDateTime;

@RegisterForReflection
public class MateriaPrimaRegistroDto {

    public Double prezzo;
    public LocalDateTime updateDate;
    public String updateUser;

    public String nome;

    public MateriaPrimaRegistroDto(Double prezzo, LocalDateTime updateDate, String updateUser, String nome) {
        this.prezzo = prezzo;
        this.updateDate = updateDate;
        this.updateUser = updateUser;
        this.nome = nome;
    }
}
