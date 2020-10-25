package io.github.gilsomanfredi.cadastropessoa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CadastroPessoaApplication.class)
public abstract class RunApplicationTests {

	private static final String USER = "admin";
	private static final String PASS = "123";

	@Autowired
	protected TestRestTemplate restTemplate;

	protected <T> ResponseEntity<T> get(String uri, Class<T> clazz) {
		return restTemplate.withBasicAuth(USER, PASS).getForEntity(uri, clazz);
	}

	protected <T> T getObject(String uri, Class<T> clazz) {
		return restTemplate.withBasicAuth(USER, PASS).getForObject(uri, clazz);
	}

	protected <T> ResponseEntity<T> post(String uri, Object body, Class<T> clazz) {
		return restTemplate.withBasicAuth(USER, PASS).postForEntity(uri, body, clazz);
	}

	protected <T> ResponseEntity<T> put(String uri, Object body, Class<T> clazz) {
		RequestCallback requestCallback = restTemplate.getRestTemplate().httpEntityCallback(body, clazz);
		ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.getRestTemplate().responseEntityExtractor(clazz);
		return restTemplate.withBasicAuth(USER, PASS).execute(uri, HttpMethod.PUT, requestCallback, responseExtractor);
	}

	protected ResponseEntity<Void> delete(String uri) {
		ResponseExtractor<ResponseEntity<Void>> responseExtractor = restTemplate.getRestTemplate().responseEntityExtractor(Void.class);
		return restTemplate.withBasicAuth(USER, PASS).execute(uri, HttpMethod.DELETE, null, responseExtractor);
	}

}
