package io.github.gilsomanfredi.cadastropessoa.test.source;

import io.github.gilsomanfredi.cadastropessoa.RunApplicationTests;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SourceIntegrationTest extends RunApplicationTests {

    @Test
    public void get() {

        ResponseEntity<String> responseGet = getRestTemplate().get("/source", String.class);

        Assert.assertEquals(HttpStatus.OK, responseGet.getStatusCode());
        Assert.assertNotNull(responseGet.getBody());
        Assert.assertEquals("https://github.com/gilsomanfredi/desafio-tecnico-softplan", responseGet.getBody());
    }

}
