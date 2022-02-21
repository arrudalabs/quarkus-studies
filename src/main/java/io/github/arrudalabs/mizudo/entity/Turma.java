package io.github.arrudalabs.mizudo.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "turmas")
public class Turma extends PanacheEntityBase {

    public static Turma criarTurma(Long codigo, String descricao) {
        var turma = new Turma();
        turma.codigo = codigo;
        turma.descricao = descricao;
        turma.persist();
        return turma;
    }


    @Id
    @Column(name = "cd_turma")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "turma_seq"
    )
    @SequenceGenerator(
            name = "turma_seq",
            sequenceName = "turma_seq"
    )
    public Long codigo;

    @NotBlank
    @Size(max = 20)
    @Column(name = "descr")
    public String descricao;

    @ManyToMany(mappedBy = "turmas")
    public Set<Aluno> alunos = new LinkedHashSet<>();
}
