package io.github.gilsomanfredi.cadastropessoa;

import io.github.gilsomanfredi.cadastropessoa.config.i18n.I18n;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CadastroPessoaApplication.class)
public abstract class RunApplicationTests {

	@Autowired
	private I18n i18n;

	private static final String USER = "admin";
	private static final String PASS = "admin@123";

	private static final String CLIENT_ID = "CadastroPessoaClient";
	private static final String CLIENT_PASS = "C4d4str0P3ss04";

	@Autowired
	protected TestRestTemplate restTemplate;

	protected <T> ResponseEntity<T> get(String uri, Class<T> clazz) {
		return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(null, getHeaders()), clazz);
	}

	protected <T> ResponseEntity<T> post(String uri, Object body, Class<T> clazz) {
		return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(body, getHeaders()), clazz);
	}

	protected <T> ResponseEntity<T> put(String uri, Object body, Class<T> clazz) {
		return restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(body, getHeaders()), clazz);
	}

	protected ResponseEntity<Void> delete(String uri) {
		return restTemplate.exchange(uri, HttpMethod.DELETE, new HttpEntity<>(null, getHeaders()), Void.class);
	}

	private String getBearerToken() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("client_id", CLIENT_ID);
		params.add("username", USER);
		params.add("password", PASS);

		ResponseEntity<Map> responseEntity = restTemplate.withBasicAuth(CLIENT_ID, CLIENT_PASS).postForEntity("/oauth/token", params, Map.class);

		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertNotNull(responseEntity.getBody());

		return responseEntity.getBody().get("access_token").toString();
	}

	private HttpHeaders getHeaders(){
		HttpHeaders authHeaders = new HttpHeaders();
		authHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		authHeaders.add("Authorization", "Bearer ".concat(getBearerToken()));
		authHeaders.add("Content-Type", "application/json");

		return authHeaders;
	}

	protected String i(String key, Object... args) {
		return i18n.getMessage(key, args);
	}

}
