package io.github.gilsomanfredi.cadastropessoa.factory.util;

import org.apache.commons.lang3.RandomStringUtils;

import br.com.caelum.stella.validation.CPFValidator;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UtilFactory {

    public String getRandonValidCpf() {

        return new CPFValidator().generateRandomValid();
    }

    public String getRandonValidEmail() {

        return RandomStringUtils.randomAlphabetic(10)
                .concat("@")
                .concat(RandomStringUtils.randomAlphabetic(5));
    }
}
