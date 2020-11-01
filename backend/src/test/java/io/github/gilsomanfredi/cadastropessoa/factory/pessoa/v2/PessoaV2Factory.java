package io.github.gilsomanfredi.cadastropessoa.factory.pessoa.v2;

import java.time.LocalDate;
import java.time.Month;

import org.apache.commons.lang3.RandomStringUtils;

import io.github.gilsomanfredi.cadastropessoa.factory.util.UtilFactory;
import io.github.gilsomanfredi.cadastropessoa.model.pessoa.v1.SexoEnum;
import io.github.gilsomanfredi.cadastropessoa.model.pessoa.v2.PessoaV2;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PessoaV2Factory {
    
    private final String URL_GET = "/pessoa/v2";
    private final String URL_GET_ID = "/pessoa/v2/%s";
    private final String URL_POST = "/pessoa/v2";
    private final String URL_PUT = "/pessoa/v2/%s";
    private final String URL_DELETE = "/pessoa/v2/%s";

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

    public PessoaV2 createPessoa() {

        PessoaV2 pessoa = new PessoaV2();
        pessoa.setNome(RandomStringUtils.randomAlphabetic(20));
        pessoa.setCpf(UtilFactory.getRandonValidCpf());
        pessoa.setSexo(SexoEnum.MASCULINO);
        pessoa.setEmail(UtilFactory.getRandonValidEmail());
        pessoa.setDataNascimento(LocalDate.of(2020, Month.JANUARY, 1));
        pessoa.setNaturalidade(RandomStringUtils.randomAlphabetic(20));
        pessoa.setNacionalidade(RandomStringUtils.randomAlphabetic(20));
        pessoa.setEndereco(RandomStringUtils.randomAlphabetic(20));

        return pessoa;
    }

}
