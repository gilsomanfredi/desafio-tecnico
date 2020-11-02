package io.github.gilsomanfredi.cadastropessoa.test.pessoa.v2;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import io.github.gilsomanfredi.cadastropessoa.RunApplicationTests;
import io.github.gilsomanfredi.cadastropessoa.factory.pessoa.v2.PessoaV2Factory;
import io.github.gilsomanfredi.cadastropessoa.factory.util.UtilFactory;
import io.github.gilsomanfredi.cadastropessoa.model.apierror.ApiError;
import io.github.gilsomanfredi.cadastropessoa.model.pessoa.v2.PessoaV2;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PessoaV2integrationTest extends RunApplicationTests {

    @Autowired
    private PessoaV2Factory pessoaV2Factory;

    @Test
    public void find_all() {

        pessoaV2Factory.insertPessoa();

        ResponseEntity<List> responseGet = getRestTemplate().get(pessoaV2Factory.getUrlGet(), List.class);

        Assert.assertEquals(HttpStatus.OK, responseGet.getStatusCode());
        Assert.assertNotNull(responseGet.getBody());
        Assert.assertFalse(responseGet.getBody().isEmpty());
    }

    @Test
    public void find_paginado() {

        pessoaV2Factory.insertPessoa();

        ResponseEntity<Map> responseGet = getRestTemplate().get(pessoaV2Factory.getUrlGetPaginado(), Map.class);

        Assert.assertEquals(HttpStatus.OK, responseGet.getStatusCode());
        Assert.assertNotNull(responseGet.getBody());
        Assert.assertFalse((boolean) responseGet.getBody().get("empty"));
    }

    @Test
    public void find_by_id_success() {

        PessoaV2 pessoa = pessoaV2Factory.insertPessoa();

        ResponseEntity<PessoaV2> responseGet = getRestTemplate().get(pessoaV2Factory.getUrlGetById(pessoa.getId()), PessoaV2.class);

        Assert.assertEquals(HttpStatus.OK, responseGet.getStatusCode());
        Assert.assertNotNull(responseGet.getBody());
        assertEquals(pessoa, responseGet.getBody());
    }

    @Test
    public void find_by_not_exists_id() {

        ResponseEntity<PessoaV2> responseGet = getRestTemplate().get(pessoaV2Factory.getUrlGetById(-1L), PessoaV2.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, responseGet.getStatusCode());
    }

    @Test
    public void insert_success() {

        ResponseEntity<PessoaV2> responsePost = getRestTemplate().post(pessoaV2Factory.getUrlPost(), pessoaV2Factory.createPessoa(), PessoaV2.class);

        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());

        PessoaV2 pessoa = responsePost.getBody();

        Assert.assertNotNull(pessoa);
        Assert.assertNotNull(pessoa.getId());
    }

    @Test
    public void insert_with_sexo_null() {

        PessoaV2 pessoa = pessoaV2Factory.createPessoa();
        pessoa.setSexo(null);

        ResponseEntity<PessoaV2> responsePost = getRestTemplate().post(pessoaV2Factory.getUrlPost(), pessoa, PessoaV2.class);

        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());

        pessoa = responsePost.getBody();

        Assert.assertNotNull(pessoa);
        Assert.assertNotNull(pessoa.getId());
    }

    @Test
    public void insert_with_nome_null() {

        PessoaV2 pessoa = pessoaV2Factory.createPessoa();
        pessoa.setNome(null);

        ResponseEntity<ApiError> responseError = getRestTemplate().post(pessoaV2Factory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotEmpty", i("pessoa.nome")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void insert_with_nome_empty() {

        PessoaV2 pessoa = pessoaV2Factory.createPessoa();
        pessoa.setNome("");

        ResponseEntity<ApiError> responseError = getRestTemplate().post(pessoaV2Factory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotEmpty", i("pessoa.nome")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void insert_with_cpf_invalido() {

        PessoaV2 pessoa = pessoaV2Factory.createPessoa();
        pessoa.setCpf(UtilFactory.randomInvalidCpf());

        ResponseEntity<ApiError> responseError = getRestTemplate().post(pessoaV2Factory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("CPF", i("pessoa.cpf")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void insert_with_cpf_duplicado() {

        PessoaV2 pessoa = pessoaV2Factory.insertPessoa();

        PessoaV2 pessoaDuplicado = pessoaV2Factory.createPessoa();
        pessoaDuplicado.setCpf(pessoa.getCpf());

        ResponseEntity<ApiError> responseError = getRestTemplate().post(pessoaV2Factory.getUrlPost(), pessoaDuplicado, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("cpf.ja.cadastrado"), apiError.getMessage());
    }

    @Test
    public void insert_with_email_invalido() {

        PessoaV2 pessoa = pessoaV2Factory.createPessoa();
        pessoa.setEmail(RandomStringUtils.randomAlphabetic(20));

        ResponseEntity<ApiError> responseError = getRestTemplate().post(pessoaV2Factory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("Email", i("pessoa.email")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void insert_with_email_nulo() {

        PessoaV2 pessoa = pessoaV2Factory.createPessoa();
        pessoa.setEmail(null);

        ResponseEntity<PessoaV2> responseError = getRestTemplate().post(pessoaV2Factory.getUrlPost(), pessoa, PessoaV2.class);

        Assert.assertEquals(HttpStatus.CREATED, responseError.getStatusCode());
    }

    @Test
    public void insert_with_email_vazio() {

        PessoaV2 pessoa = pessoaV2Factory.createPessoa();
        pessoa.setEmail("");

        ResponseEntity<PessoaV2> responseError = getRestTemplate().post(pessoaV2Factory.getUrlPost(), pessoa, PessoaV2.class);

        Assert.assertEquals(HttpStatus.CREATED, responseError.getStatusCode());
    }

    @Test
    public void insert_with_data_nascimento_null() {

        PessoaV2 pessoa = pessoaV2Factory.createPessoa();
        pessoa.setDataNascimento(null);

        ResponseEntity<ApiError> responseError = getRestTemplate().post(pessoaV2Factory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotNull", i("pessoa.dataNascimento")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void insert_with_data_nascimento_maior_que_hoje() {

        PessoaV2 pessoa = pessoaV2Factory.createPessoa();
        pessoa.setDataNascimento(LocalDate.now().plusDays(1));

        ResponseEntity<ApiError> responseError = getRestTemplate().post(pessoaV2Factory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("data.nascimento.invalida"), apiError.getMessage());
    }

    @Test
    public void insert_with_endereco_null() {

        PessoaV2 pessoa = pessoaV2Factory.createPessoa();
        pessoa.setEndereco(null);

        ResponseEntity<ApiError> responseError = getRestTemplate().post(pessoaV2Factory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotEmpty", i("pessoa.endereco")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void insert_with_endereco_empty() {

        PessoaV2 pessoa = pessoaV2Factory.createPessoa();
        pessoa.setEndereco("");

        ResponseEntity<ApiError> responseError = getRestTemplate().post(pessoaV2Factory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotEmpty", i("pessoa.endereco")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void update_success() {

        PessoaV2 pessoa = pessoaV2Factory.insertPessoa();

        pessoa.setNome(RandomStringUtils.randomAlphabetic(20));
        pessoa.setNaturalidade(RandomStringUtils.randomAlphabetic(20));
        pessoa.setNacionalidade(RandomStringUtils.randomAlphabetic(20));

        ResponseEntity<PessoaV2> responsePut = getRestTemplate().put(pessoaV2Factory.getUrlPut(pessoa.getId()), pessoa, PessoaV2.class);

        Assert.assertEquals(HttpStatus.OK, responsePut.getStatusCode());
        Assert.assertNotNull(responsePut.getBody());
        assertEquals(pessoa, responsePut.getBody());
    }

    @Test
    public void update_with_nome_null() {

        PessoaV2 pessoa = pessoaV2Factory.insertPessoa();
        pessoa.setNome(null);

        ResponseEntity<ApiError> responseError = getRestTemplate().put(pessoaV2Factory.getUrlPut(pessoa.getId()), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotEmpty", i("pessoa.nome")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void update_with_nome_empty() {

        PessoaV2 pessoa = pessoaV2Factory.insertPessoa();
        pessoa.setNome("");

        ResponseEntity<ApiError> responseError = getRestTemplate().put(pessoaV2Factory.getUrlPut(pessoa.getId()), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotEmpty", i("pessoa.nome")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void update_with_cpf_invalido() {

        PessoaV2 pessoa = pessoaV2Factory.insertPessoa();
        pessoa.setCpf(UtilFactory.randomInvalidCpf());

        ResponseEntity<ApiError> responseError = getRestTemplate().put(pessoaV2Factory.getUrlPut(pessoa.getId()), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("CPF", i("pessoa.cpf")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void update_with_cpf_duplicado() {

        PessoaV2 pessoa = pessoaV2Factory.insertPessoa();

        PessoaV2 pessoa2 = pessoaV2Factory.insertPessoa();

        pessoa2.setCpf(pessoa.getCpf());

        ResponseEntity<ApiError> responseError = getRestTemplate().put(pessoaV2Factory.getUrlPut(pessoa2.getId()), pessoa2, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("cpf.ja.cadastrado"), apiError.getMessage());
    }

    @Test
    public void update_with_email_invalido() {

        PessoaV2 pessoa = pessoaV2Factory.insertPessoa();
        pessoa.setEmail(RandomStringUtils.randomAlphabetic(20));

        ResponseEntity<ApiError> responseError = getRestTemplate().put(pessoaV2Factory.getUrlPut(pessoa.getId()), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("Email", i("pessoa.email")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void update_with_email_nulo() {

        PessoaV2 pessoa = pessoaV2Factory.insertPessoa();
        pessoa.setEmail(null);

        ResponseEntity<PessoaV2> responseError = getRestTemplate().put(pessoaV2Factory.getUrlPut(pessoa.getId()), pessoa, PessoaV2.class);

        Assert.assertEquals(HttpStatus.OK, responseError.getStatusCode());
    }

    @Test
    public void update_with_email_vazio() {

        PessoaV2 pessoa = pessoaV2Factory.insertPessoa();
        pessoa.setEmail("");

        ResponseEntity<PessoaV2> responseError = getRestTemplate().put(pessoaV2Factory.getUrlPut(pessoa.getId()), pessoa, PessoaV2.class);

        Assert.assertEquals(HttpStatus.OK, responseError.getStatusCode());
    }

    @Test
    public void update_with_data_nascimento_null() {

        PessoaV2 pessoa = pessoaV2Factory.insertPessoa();
        pessoa.setDataNascimento(null);

        ResponseEntity<ApiError> responseError = getRestTemplate().put(pessoaV2Factory.getUrlPut(pessoa.getId()), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotNull", i("pessoa.dataNascimento")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void update_with_data_nascimento_maior_que_hoje() {

        PessoaV2 pessoa = pessoaV2Factory.insertPessoa();
        pessoa.setDataNascimento(LocalDate.now().plusDays(1));

        ResponseEntity<ApiError> responseError = getRestTemplate().put(pessoaV2Factory.getUrlPut(pessoa.getId()), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("data.nascimento.invalida"), apiError.getMessage());
    }

    @Test
    public void update_with_endereco_null() {

        PessoaV2 pessoa = pessoaV2Factory.insertPessoa();
        pessoa.setEndereco(null);

        ResponseEntity<ApiError> responseError = getRestTemplate().put(pessoaV2Factory.getUrlPut(pessoa.getId()), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotEmpty", i("pessoa.endereco")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void update_with_endereco_empty() {

        PessoaV2 pessoa = pessoaV2Factory.insertPessoa();
        pessoa.setEndereco("");

        ResponseEntity<ApiError> responseError = getRestTemplate().put(pessoaV2Factory.getUrlPut(pessoa.getId()), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotEmpty", i("pessoa.endereco")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void update_with_not_exists_id() {

        PessoaV2 pessoa = pessoaV2Factory.insertPessoa();

        ResponseEntity<PessoaV2> responsePut = getRestTemplate().put(pessoaV2Factory.getUrlPut(-1L), pessoa, PessoaV2.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, responsePut.getStatusCode());
    }

    @Test
    public void delete_success() {

        PessoaV2 pessoa = pessoaV2Factory.insertPessoa();

        ResponseEntity<Void> responseDelete = getRestTemplate().delete(pessoaV2Factory.getUrlDelete(pessoa.getId()));

        Assert.assertEquals(HttpStatus.NO_CONTENT, responseDelete.getStatusCode());

        ResponseEntity<PessoaV2> responseGet = getRestTemplate().get(pessoaV2Factory.getUrlGetById(pessoa.getId()), PessoaV2.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, responseGet.getStatusCode());
    }

    @Test
    public void delete_with_not_exists_id() {

        ResponseEntity<Void> responsePut = getRestTemplate().delete(pessoaV2Factory.getUrlDelete(-1L));

        Assert.assertEquals(HttpStatus.NOT_FOUND, responsePut.getStatusCode());
    }

    private void assertEquals(PessoaV2 pessoaExpected, PessoaV2 pessoa) {

        Assert.assertEquals(pessoaExpected.getId(), pessoa.getId());
        Assert.assertEquals(pessoaExpected.getNome(), pessoa.getNome());
        Assert.assertEquals(pessoaExpected.getCpf(), pessoa.getCpf());
        Assert.assertEquals(pessoaExpected.getEmail(), pessoa.getEmail());
        Assert.assertEquals(pessoaExpected.getDataNascimento(), pessoa.getDataNascimento());
        Assert.assertEquals(pessoaExpected.getNaturalidade(), pessoa.getNaturalidade());
        Assert.assertEquals(pessoaExpected.getNacionalidade(), pessoa.getNacionalidade());
        Assert.assertEquals(pessoaExpected.getEndereco(), pessoa.getEndereco());
    }
}
