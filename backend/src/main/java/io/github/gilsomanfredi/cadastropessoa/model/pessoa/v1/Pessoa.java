package io.github.gilsomanfredi.cadastropessoa.model.pessoa.v1;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pessoa {

    @Id
    private Long id;

    @NotEmpty
    @Size(max = 50)
    private String nome;

    @CPF
    private String cpf;

    private SexoEnum sexo;

    @Email
    @Size(max = 50)
    private String email;

    @NotNull
    private LocalDate dataNascimento;

    @Size(max = 50)
    private String naturalidade;

    @Size(max = 50)
    private String nacionalidade;

    private LocalDate dataInclusao;

    private LocalDate dataAlteracao;

}
