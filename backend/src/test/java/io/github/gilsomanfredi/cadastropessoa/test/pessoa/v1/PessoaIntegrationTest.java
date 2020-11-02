package io.github.gilsomanfredi.cadastropessoa.test.pessoa.v1;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import io.github.gilsomanfredi.cadastropessoa.RunApplicationTests;
import io.github.gilsomanfredi.cadastropessoa.factory.pessoa.v1.PessoaFactory;
import io.github.gilsomanfredi.cadastropessoa.factory.util.UtilFactory;
import io.github.gilsomanfredi.cadastropessoa.model.apierror.ApiError;
import io.github.gilsomanfredi.cadastropessoa.model.pessoa.v1.Pessoa;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PessoaIntegrationTest extends RunApplicationTests {

    @Autowired
    private PessoaFactory pessoaFactory;

    @Test
    public void find_all() {

        pessoaFactory.insertPessoa();

        ResponseEntity<List> responseGet = getRestTemplate().get(pessoaFactory.getUrlGet(), List.class);

        Assert.assertEquals(HttpStatus.OK, responseGet.getStatusCode());
        Assert.assertNotNull(responseGet.getBody());
        Assert.assertFalse(responseGet.getBody().isEmpty());
    }

    @Test
    public void find_paginado() {

        pessoaFactory.insertPessoa();

        ResponseEntity<Map> responseGet = getRestTemplate().get(pessoaFactory.getUrlGetPaginado(), Map.class);

        Assert.assertEquals(HttpStatus.OK, responseGet.getStatusCode());
        Assert.assertNotNull(responseGet.getBody());
        Assert.assertFalse((boolean) responseGet.getBody().get("empty"));
    }

    @Test
    public void find_by_id_success() {

        Pessoa pessoa = pessoaFactory.insertPessoa();

        ResponseEntity<Pessoa> responseGet = getRestTemplate().get(pessoaFactory.getUrlGetById(pessoa.getId()), Pessoa.class);

        Assert.assertEquals(HttpStatus.OK, responseGet.getStatusCode());
        Assert.assertNotNull(responseGet.getBody());
        assertEquals(pessoa, responseGet.getBody());
    }

    @Test
    public void find_by_not_exists_id() {

        ResponseEntity<Pessoa> responseGet = getRestTemplate().get(pessoaFactory.getUrlGetById(-1L), Pessoa.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, responseGet.getStatusCode());
    }

    @Test
    public void insert_success() {

        ResponseEntity<Pessoa> responsePost = getRestTemplate().post(pessoaFactory.getUrlPost(), pessoaFactory.createPessoa(), Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());

        Pessoa pessoa = responsePost.getBody();

        Assert.assertNotNull(pessoa);
        Assert.assertNotNull(pessoa.getId());
    }

    @Test
    public void insert_with_sexo_null() {

        Pessoa pessoa = pessoaFactory.createPessoa();
        pessoa.setSexo(null);

        ResponseEntity<Pessoa> responsePost = getRestTemplate().post(pessoaFactory.getUrlPost(), pessoa, Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());

        pessoa = responsePost.getBody();

        Assert.assertNotNull(pessoa);
        Assert.assertNotNull(pessoa.getId());
    }

    @Test
    public void insert_with_nome_null() {

        Pessoa pessoa = pessoaFactory.createPessoa();
        pessoa.setNome(null);

        ResponseEntity<ApiError> responseError = getRestTemplate().post(pessoaFactory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotEmpty", i("pessoa.nome")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void insert_with_nome_empty() {

        Pessoa pessoa = pessoaFactory.createPessoa();
        pessoa.setNome("");

        ResponseEntity<ApiError> responseError = getRestTemplate().post(pessoaFactory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotEmpty", i("pessoa.nome")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void insert_with_cpf_invalido() {

        Pessoa pessoa = pessoaFactory.createPessoa();
        pessoa.setCpf(UtilFactory.randomInvalidCpf());

        ResponseEntity<ApiError> responseError = getRestTemplate().post(pessoaFactory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("CPF", i("pessoa.cpf")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void insert_with_cpf_duplicado() {

        Pessoa pessoa = pessoaFactory.insertPessoa();

        Pessoa pessoaDuplicado = pessoaFactory.createPessoa();
        pessoaDuplicado.setCpf(pessoa.getCpf());

        ResponseEntity<ApiError> responseError = getRestTemplate().post(pessoaFactory.getUrlPost(), pessoaDuplicado, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("cpf.ja.cadastrado"), apiError.getMessage());
    }

    @Test
    public void insert_with_email_invalido() {

        Pessoa pessoa = pessoaFactory.createPessoa();
        pessoa.setEmail(RandomStringUtils.randomAlphabetic(20));

        ResponseEntity<ApiError> responseError = getRestTemplate().post(pessoaFactory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("Email", i("pessoa.email")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void insert_with_email_nulo() {

        Pessoa pessoa = pessoaFactory.createPessoa();
        pessoa.setEmail(null);

        ResponseEntity<Pessoa> responseError = getRestTemplate().post(pessoaFactory.getUrlPost(), pessoa, Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responseError.getStatusCode());
    }

    @Test
    public void insert_with_email_vazio() {

        Pessoa pessoa = pessoaFactory.createPessoa();
        pessoa.setEmail("");

        ResponseEntity<Pessoa> responseError = getRestTemplate().post(pessoaFactory.getUrlPost(), pessoa, Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responseError.getStatusCode());
    }

    @Test
    public void insert_with_data_nascimento_null() {

        Pessoa pessoa = pessoaFactory.createPessoa();
        pessoa.setDataNascimento(null);

        ResponseEntity<ApiError> responseError = getRestTemplate().post(pessoaFactory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotNull", i("pessoa.dataNascimento")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void insert_with_data_nascimento_maior_que_hoje() {

        Pessoa pessoa = pessoaFactory.createPessoa();
        pessoa.setDataNascimento(LocalDate.now().plusDays(1));

        ResponseEntity<ApiError> responseError = getRestTemplate().post(pessoaFactory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("data.nascimento.invalida"), apiError.getMessage());
    }

    @Test
    public void update_success() {

        Pessoa pessoa = pessoaFactory.insertPessoa();

        pessoa.setNome(RandomStringUtils.randomAlphabetic(20));
        pessoa.setNaturalidade(RandomStringUtils.randomAlphabetic(20));
        pessoa.setNacionalidade(RandomStringUtils.randomAlphabetic(20));

        ResponseEntity<Pessoa> responsePut = getRestTemplate().put(pessoaFactory.getUrlPut(pessoa.getId()), pessoa, Pessoa.class);

        Assert.assertEquals(HttpStatus.OK, responsePut.getStatusCode());
        Assert.assertNotNull(responsePut.getBody());
        assertEquals(pessoa, responsePut.getBody());
    }

    @Test
    public void update_with_nome_null() {

        Pessoa pessoa = pessoaFactory.insertPessoa();
        pessoa.setNome(null);

        ResponseEntity<ApiError> responseError = getRestTemplate().put(pessoaFactory.getUrlPut(pessoa.getId()), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotEmpty", i("pessoa.nome")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void update_with_nome_empty() {

        Pessoa pessoa = pessoaFactory.insertPessoa();
        pessoa.setNome("");

        ResponseEntity<ApiError> responseError = getRestTemplate().put(pessoaFactory.getUrlPut(pessoa.getId()), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotEmpty", i("pessoa.nome")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void update_with_cpf_invalido() {

        Pessoa pessoa = pessoaFactory.insertPessoa();
        pessoa.setCpf(UtilFactory.randomInvalidCpf());

        ResponseEntity<ApiError> responseError = getRestTemplate().put(pessoaFactory.getUrlPut(pessoa.getId()), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("CPF", i("pessoa.cpf")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void update_with_cpf_duplicado() {

        Pessoa pessoa = pessoaFactory.insertPessoa();

        Pessoa pessoa2 = pessoaFactory.insertPessoa();

        pessoa2.setCpf(pessoa.getCpf());

        ResponseEntity<ApiError> responseError = getRestTemplate().put(pessoaFactory.getUrlPut(pessoa2.getId()), pessoa2, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("cpf.ja.cadastrado"), apiError.getMessage());
    }

    @Test
    public void update_with_email_invalido() {

        Pessoa pessoa = pessoaFactory.insertPessoa();
        pessoa.setEmail(RandomStringUtils.randomAlphabetic(20));

        ResponseEntity<ApiError> responseError = getRestTemplate().put(pessoaFactory.getUrlPut(pessoa.getId()), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("Email", i("pessoa.email")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void update_with_email_nulo() {

        Pessoa pessoa = pessoaFactory.insertPessoa();
        pessoa.setEmail(null);

        ResponseEntity<Pessoa> responseError = getRestTemplate().put(pessoaFactory.getUrlPut(pessoa.getId()), pessoa, Pessoa.class);

        Assert.assertEquals(HttpStatus.OK, responseError.getStatusCode());
    }

    @Test
    public void update_with_email_vazio() {

        Pessoa pessoa = pessoaFactory.insertPessoa();
        pessoa.setEmail("");

        ResponseEntity<Pessoa> responseError = getRestTemplate().put(pessoaFactory.getUrlPut(pessoa.getId()), pessoa, Pessoa.class);

        Assert.assertEquals(HttpStatus.OK, responseError.getStatusCode());
    }

    @Test
    public void update_with_data_nascimento_null() {

        Pessoa pessoa = pessoaFactory.insertPessoa();
        pessoa.setDataNascimento(null);

        ResponseEntity<ApiError> responseError = getRestTemplate().put(pessoaFactory.getUrlPut(pessoa.getId()), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotNull", i("pessoa.dataNascimento")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void update_with_data_nascimento_maior_que_hoje() {

        Pessoa pessoa = pessoaFactory.insertPessoa();
        pessoa.setDataNascimento(LocalDate.now().plusDays(1));

        ResponseEntity<ApiError> responseError = getRestTemplate().put(pessoaFactory.getUrlPut(pessoa.getId()), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("data.nascimento.invalida"), apiError.getMessage());
    }

    @Test
    public void update_with_not_exists_id() {

        Pessoa pessoa = pessoaFactory.createPessoa();

        ResponseEntity<Pessoa> responsePut = getRestTemplate().put(pessoaFactory.getUrlPut(-1L), pessoa, Pessoa.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, responsePut.getStatusCode());
    }

    @Test
    public void delete_success() {

        Pessoa pessoa = pessoaFactory.insertPessoa();

        ResponseEntity<Void> responseDelete = getRestTemplate().delete(pessoaFactory.getUrlDelete(pessoa.getId()));

        Assert.assertEquals(HttpStatus.NO_CONTENT, responseDelete.getStatusCode());

        ResponseEntity<Pessoa> responseGet = getRestTemplate().get(pessoaFactory.getUrlGetById(pessoa.getId()), Pessoa.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, responseGet.getStatusCode());
    }

    @Test
    public void delete_with_not_exists_id() {

        ResponseEntity<Void> responsePut = getRestTemplate().delete(pessoaFactory.getUrlDelete(-1L));

        Assert.assertEquals(HttpStatus.NOT_FOUND, responsePut.getStatusCode());
    }

    private void assertEquals(Pessoa pessoaExpected, Pessoa pessoa) {

        Assert.assertEquals(pessoaExpected.getId(), pessoa.getId());
        Assert.assertEquals(pessoaExpected.getNome(), pessoa.getNome());
        Assert.assertEquals(pessoaExpected.getCpf(), pessoa.getCpf());
        Assert.assertEquals(pessoaExpected.getEmail(), pessoa.getEmail());
        Assert.assertEquals(pessoaExpected.getDataNascimento(), pessoa.getDataNascimento());
        Assert.assertEquals(pessoaExpected.getNaturalidade(), pessoa.getNaturalidade());
        Assert.assertEquals(pessoaExpected.getNacionalidade(), pessoa.getNacionalidade());
    }
}
