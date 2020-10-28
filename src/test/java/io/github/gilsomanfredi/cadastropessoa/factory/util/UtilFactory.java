package io.github.gilsomanfredi.cadastropessoa.factory.util;

import br.com.caelum.stella.tinytype.CPF;
import org.apache.commons.lang3.RandomStringUtils;

import br.com.caelum.stella.validation.CPFValidator;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UtilFactory {

    public String getRandonValidCpf() {

        return new CPFValidator().generateRandomValid();
    }

    public static String randomInvalidCpf() {
        String generatedCpf = RandomStringUtils.randomNumeric(11);
        CPF cpf = new CPF(generatedCpf);
        return !cpf.isValido() ? generatedCpf : randomInvalidCpf();
    }

    public String getRandonValidEmail() {

        return RandomStringUtils.randomAlphabetic(10)
                .concat("@")
                .concat(RandomStringUtils.randomAlphabetic(5));
    }
}
