package io.github.arrudalabs.mizudo.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "membros")
public class Aluno extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = Membro.SEQUENCE_NAME)
    private Long matricula;

    @Size(max = 80)
    public String nome;

    @Size(max = 20)
    public String apelido;

    @ManyToMany
    @JoinTable(name = "turmas_alunos",
            joinColumns = {
                    @JoinColumn(name = "matricula")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "cd_turma")
            })
    public Set<Turma> turmas = new LinkedHashSet<>();
}
