package io.github.arrudalabs.mizudo.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "membros")
@SequenceGenerator(
        name = "membros_seq",
        sequenceName = "membros_seq"
)
public class Membro extends PanacheEntityBase {

    public static final String SEQUENCE_NAME = "membros_seq";

    public static Membro criarMembro(String nome, String apelido) {
        Membro membro = new Membro();
        membro.apelido = apelido;
        membro.nome = nome;
        membro.persistAndFlush();
        return membro;
    }

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = Membro.SEQUENCE_NAME
    )
    @Column(name = "matricula")
    public Long matricula;

    @Size(max = 80)
    public String nome;

    @Size(max = 20)
    public String apelido;

}
