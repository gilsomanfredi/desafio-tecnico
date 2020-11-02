package io.github.gilsomanfredi.cadastropessoa;

import io.github.gilsomanfredi.cadastropessoa.config.i18n.I18n;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CadastroPessoaApplication.class)
public abstract class RunApplicationTests {

	@Autowired
	private I18n i18n;

	@Getter
	@Autowired
	private CustomRestTemplate restTemplate;

	protected String i(String key, Object... args) {
		return i18n.getMessage(key, args);
	}

}
