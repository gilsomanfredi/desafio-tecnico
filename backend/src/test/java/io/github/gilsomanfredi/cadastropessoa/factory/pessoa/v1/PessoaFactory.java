package io.github.gilsomanfredi.cadastropessoa.factory.pessoa.v1;

import java.time.LocalDate;
import java.time.Month;

import org.apache.commons.lang3.RandomStringUtils;

import io.github.gilsomanfredi.cadastropessoa.factory.util.UtilFactory;
import io.github.gilsomanfredi.cadastropessoa.model.pessoa.v1.Pessoa;
import io.github.gilsomanfredi.cadastropessoa.model.pessoa.v1.SexoEnum;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PessoaFactory {

    private final String URL_GET = "/pessoa";
    private final String URL_GET_ID = "/pessoa/%s";
    private final String URL_POST = "/pessoa";
    private final String URL_PUT = "/pessoa/%s";
    private final String URL_DELETE = "/pessoa/%s";

    public String getUrlGet() {
        return URL_GET;
    }

    public String getUrlGetById(Long id) {
        return String.format(URL_GET_ID, id);
    }

    public String getUrlPost() {
        return URL_POST;
    }

    public String getUrlPut(Long id) {
        return String.format(URL_PUT, id);
    }

    public String getUrlDelete(Long id) {
        return String.format(URL_DELETE, id);
    }

    public Pessoa createPessoa() {

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(RandomStringUtils.randomAlphabetic(20));
        pessoa.setCpf(UtilFactory.getRandonValidCpf());
        pessoa.setSexo(SexoEnum.MASCULINO);
        pessoa.setEmail(UtilFactory.getRandonValidEmail());
        pessoa.setDataNascimento(LocalDate.of(2020, Month.JANUARY, 1));
        pessoa.setNaturalidade(RandomStringUtils.randomAlphabetic(20));
        pessoa.setNacionalidade(RandomStringUtils.randomAlphabetic(20));
        pessoa.setNacionalidade(RandomStringUtils.randomAlphabetic(20));

        return pessoa;
    }

}