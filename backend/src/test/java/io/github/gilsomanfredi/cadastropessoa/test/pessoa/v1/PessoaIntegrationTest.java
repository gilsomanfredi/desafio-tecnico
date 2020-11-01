package io.github.gilsomanfredi.cadastropessoa.test.pessoa.v1;

import java.time.LocalDate;
import java.util.Map;

import io.github.gilsomanfredi.cadastropessoa.RunApplicationTests;
import io.github.gilsomanfredi.cadastropessoa.factory.pessoa.v1.PessoaFactory;
import io.github.gilsomanfredi.cadastropessoa.factory.util.UtilFactory;
import io.github.gilsomanfredi.cadastropessoa.model.apierror.ApiError;
import io.github.gilsomanfredi.cadastropessoa.model.pessoa.v1.Pessoa;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PessoaIntegrationTest extends RunApplicationTests {

    @Test
    public void find_all() {

        ResponseEntity<Pessoa> responsePost = post(PessoaFactory.getUrlPost(), PessoaFactory.createPessoa(), Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());

        ResponseEntity<Map> responseGet = get(PessoaFactory.getUrlGet(), Map.class);

        Assert.assertEquals(HttpStatus.OK, responseGet.getStatusCode());
        Assert.assertNotNull(responseGet.getBody());
        Assert.assertFalse((boolean) responseGet.getBody().get("empty"));
    }

    @Test
    public void find_by_id_success() {

        ResponseEntity<Pessoa> responsePost = post(PessoaFactory.getUrlPost(), PessoaFactory.createPessoa(), Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());

        Pessoa pessoa = responsePost.getBody();

        Assert.assertNotNull(pessoa);
        Assert.assertNotNull(pessoa.getId());

        ResponseEntity<Pessoa> responseGet = get(PessoaFactory.getUrlGetById(pessoa.getId()), Pessoa.class);

        Assert.assertEquals(HttpStatus.OK, responseGet.getStatusCode());
        Assert.assertNotNull(responseGet.getBody());
        assertEquals(pessoa, responseGet.getBody());
    }

    @Test
    public void find_by_not_exists_id() {

        ResponseEntity<Pessoa> responseGet = get(PessoaFactory.getUrlGetById(-1L), Pessoa.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, responseGet.getStatusCode());
    }

    @Test
    public void insert_success() {

        ResponseEntity<Pessoa> responsePost = post(PessoaFactory.getUrlPost(), PessoaFactory.createPessoa(), Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());

        Pessoa pessoa = responsePost.getBody();

        Assert.assertNotNull(pessoa);
        Assert.assertNotNull(pessoa.getId());
    }

    @Test
    public void insert_with_sexo_null() {

        Pessoa pessoa = PessoaFactory.createPessoa();
        pessoa.setSexo(null);

        ResponseEntity<Pessoa> responsePost = post(PessoaFactory.getUrlPost(), pessoa, Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());

        pessoa = responsePost.getBody();

        Assert.assertNotNull(pessoa);
        Assert.assertNotNull(pessoa.getId());
    }

    @Test
    public void insert_with_nome_null() {

        Pessoa pessoa = PessoaFactory.createPessoa();
        pessoa.setNome(null);

        ResponseEntity<ApiError> responseError = post(PessoaFactory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotEmpty", i("pessoa.nome")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void insert_with_nome_empty() {

        Pessoa pessoa = PessoaFactory.createPessoa();
        pessoa.setNome("");

        ResponseEntity<ApiError> responseError = post(PessoaFactory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotEmpty", i("pessoa.nome")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void insert_with_cpf_invalido() {

        Pessoa pessoa = PessoaFactory.createPessoa();
        pessoa.setCpf(UtilFactory.randomInvalidCpf());

        ResponseEntity<ApiError> responseError = post(PessoaFactory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("CPF", i("pessoa.cpf")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void insert_with_cpf_duplicado() {

        Pessoa pessoa = PessoaFactory.createPessoa();

        ResponseEntity<Pessoa> responsePost = post(PessoaFactory.getUrlPost(), pessoa, Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());

        Pessoa pessoaDuplicado = PessoaFactory.createPessoa();
        pessoaDuplicado.setCpf(pessoa.getCpf());

        ResponseEntity<ApiError> responseError = post(PessoaFactory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("cpf.ja.cadastrado"), apiError.getMessage());
    }

    @Test
    public void insert_with_email_invalido() {

        Pessoa pessoa = PessoaFactory.createPessoa();
        pessoa.setEmail(RandomStringUtils.randomAlphabetic(20));

        ResponseEntity<ApiError> responseError = post(PessoaFactory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("Email", i("pessoa.email")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void insert_with_email_nulo() {

        Pessoa pessoa = PessoaFactory.createPessoa();
        pessoa.setEmail(null);

        ResponseEntity<Pessoa> responseError = post(PessoaFactory.getUrlPost(), pessoa, Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responseError.getStatusCode());
    }

    @Test
    public void insert_with_email_vazio() {

        Pessoa pessoa = PessoaFactory.createPessoa();
        pessoa.setEmail("");

        ResponseEntity<Pessoa> responseError = post(PessoaFactory.getUrlPost(), pessoa, Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responseError.getStatusCode());
    }

    @Test
    public void insert_with_data_nascimento_null() {

        Pessoa pessoa = PessoaFactory.createPessoa();
        pessoa.setDataNascimento(null);

        ResponseEntity<ApiError> responseError = post(PessoaFactory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotNull", i("pessoa.dataNascimento")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void insert_with_data_nascimento_maior_que_hoje() {

        Pessoa pessoa = PessoaFactory.createPessoa();
        pessoa.setDataNascimento(LocalDate.now().plusDays(1));

        ResponseEntity<ApiError> responseError = post(PessoaFactory.getUrlPost(), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("data.nascimento.invalida"), apiError.getMessage());
    }

    @Test
    public void update_success() {

        ResponseEntity<Pessoa> responsePost = post(PessoaFactory.getUrlPost(), PessoaFactory.createPessoa(), Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());

        Pessoa pessoa = responsePost.getBody();

        Assert.assertNotNull(pessoa);
        Assert.assertNotNull(pessoa.getId());

        pessoa.setNome(RandomStringUtils.randomAlphabetic(20));
        pessoa.setNaturalidade(RandomStringUtils.randomAlphabetic(20));
        pessoa.setNacionalidade(RandomStringUtils.randomAlphabetic(20));

        ResponseEntity<Pessoa> responsePut = put(PessoaFactory.getUrlPut(pessoa.getId()), pessoa, Pessoa.class);

        Assert.assertEquals(HttpStatus.OK, responsePut.getStatusCode());
        Assert.assertNotNull(responsePut.getBody());
        assertEquals(pessoa, responsePut.getBody());
    }

    @Test
    public void update_with_nome_null() {

        ResponseEntity<Pessoa> responsePost = post(PessoaFactory.getUrlPost(), PessoaFactory.createPessoa(), Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());

        Pessoa pessoa = responsePost.getBody();

        Assert.assertNotNull(pessoa);
        Assert.assertNotNull(pessoa.getId());

        pessoa.setNome(null);

        ResponseEntity<ApiError> responseError = put(PessoaFactory.getUrlPut(pessoa.getId()), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotEmpty", i("pessoa.nome")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void update_with_nome_empty() {

        ResponseEntity<Pessoa> responsePost = post(PessoaFactory.getUrlPost(), PessoaFactory.createPessoa(), Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());

        Pessoa pessoa = responsePost.getBody();

        Assert.assertNotNull(pessoa);
        Assert.assertNotNull(pessoa.getId());

        pessoa.setNome("");

        ResponseEntity<ApiError> responseError = put(PessoaFactory.getUrlPut(pessoa.getId()), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotEmpty", i("pessoa.nome")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void update_with_cpf_invalido() {

        ResponseEntity<Pessoa> responsePost = post(PessoaFactory.getUrlPost(), PessoaFactory.createPessoa(), Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());

        Pessoa pessoa = responsePost.getBody();

        Assert.assertNotNull(pessoa);
        Assert.assertNotNull(pessoa.getId());

        pessoa.setCpf(UtilFactory.randomInvalidCpf());

        ResponseEntity<ApiError> responseError = put(PessoaFactory.getUrlPut(pessoa.getId()), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("CPF", i("pessoa.cpf")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void update_with_cpf_duplicado() {

        Pessoa pessoa = PessoaFactory.createPessoa();
        ResponseEntity<Pessoa> responsePost = post(PessoaFactory.getUrlPost(), pessoa, Pessoa.class);
        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());
        pessoa = responsePost.getBody();
        Assert.assertNotNull(pessoa);

        Pessoa pessoa2 = PessoaFactory.createPessoa();
        responsePost = post(PessoaFactory.getUrlPost(), pessoa2, Pessoa.class);
        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());
        pessoa2 = responsePost.getBody();
        Assert.assertNotNull(pessoa2);

        pessoa2.setCpf(pessoa.getCpf());

        ResponseEntity<ApiError> responseError = put(PessoaFactory.getUrlPut(pessoa2.getId()), pessoa2, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("cpf.ja.cadastrado"), apiError.getMessage());
    }

    @Test
    public void update_with_email_invalido() {

        ResponseEntity<Pessoa> responsePost = post(PessoaFactory.getUrlPost(), PessoaFactory.createPessoa(), Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());

        Pessoa pessoa = responsePost.getBody();

        Assert.assertNotNull(pessoa);
        Assert.assertNotNull(pessoa.getId());

        pessoa.setEmail(RandomStringUtils.randomAlphabetic(20));

        ResponseEntity<ApiError> responseError = put(PessoaFactory.getUrlPut(pessoa.getId()), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("Email", i("pessoa.email")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void update_with_email_nulo() {

        ResponseEntity<Pessoa> responsePost = post(PessoaFactory.getUrlPost(), PessoaFactory.createPessoa(), Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());

        Pessoa pessoa = responsePost.getBody();

        Assert.assertNotNull(pessoa);
        Assert.assertNotNull(pessoa.getId());

        pessoa.setEmail(null);

        ResponseEntity<Pessoa> responseError = put(PessoaFactory.getUrlPut(pessoa.getId()), pessoa, Pessoa.class);

        Assert.assertEquals(HttpStatus.OK, responseError.getStatusCode());
    }

    @Test
    public void update_with_email_vazio() {

        ResponseEntity<Pessoa> responsePost = post(PessoaFactory.getUrlPost(), PessoaFactory.createPessoa(), Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());

        Pessoa pessoa = responsePost.getBody();

        Assert.assertNotNull(pessoa);
        Assert.assertNotNull(pessoa.getId());

        pessoa.setEmail("");

        ResponseEntity<Pessoa> responseError = put(PessoaFactory.getUrlPut(pessoa.getId()), pessoa, Pessoa.class);

        Assert.assertEquals(HttpStatus.OK, responseError.getStatusCode());
    }

    @Test
    public void update_with_data_nascimento_null() {

        ResponseEntity<Pessoa> responsePost = post(PessoaFactory.getUrlPost(), PessoaFactory.createPessoa(), Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());

        Pessoa pessoa = responsePost.getBody();

        Assert.assertNotNull(pessoa);
        Assert.assertNotNull(pessoa.getId());

        pessoa.setDataNascimento(null);

        ResponseEntity<ApiError> responseError = put(PessoaFactory.getUrlPut(pessoa.getId()), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("mensagem.validacao.dados"), apiError.getMessage());
        Assert.assertFalse(apiError.getErrors().isEmpty());
        Assert.assertEquals(i("NotNull", i("pessoa.dataNascimento")), apiError.getErrors().get(0).getError());
    }

    @Test
    public void update_with_data_nascimento_maior_que_hoje() {

        ResponseEntity<Pessoa> responsePost = post(PessoaFactory.getUrlPost(), PessoaFactory.createPessoa(), Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());

        Pessoa pessoa = responsePost.getBody();

        Assert.assertNotNull(pessoa);
        Assert.assertNotNull(pessoa.getId());

        pessoa.setDataNascimento(LocalDate.now().plusDays(1));

        ResponseEntity<ApiError> responseError = put(PessoaFactory.getUrlPut(pessoa.getId()), pessoa, ApiError.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseError.getStatusCode());

        ApiError apiError = responseError.getBody();

        Assert.assertNotNull(apiError);
        Assert.assertEquals(i("data.nascimento.invalida"), apiError.getMessage());
    }

    @Test
    public void update_with_not_exists_id() {

        Pessoa pessoa = PessoaFactory.createPessoa();

        ResponseEntity<Pessoa> responsePut = put(PessoaFactory.getUrlPut(-1L), pessoa, Pessoa.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, responsePut.getStatusCode());
    }

    @Test
    public void delete_success() {

        ResponseEntity<Pessoa> responsePost = post(PessoaFactory.getUrlPost(), PessoaFactory.createPessoa(), Pessoa.class);

        Assert.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());

        Pessoa pessoa = responsePost.getBody();

        Assert.assertNotNull(pessoa);
        Assert.assertNotNull(pessoa.getId());

        ResponseEntity<Void> responseDelete = delete(PessoaFactory.getUrlDelete(pessoa.getId()));

        Assert.assertEquals(HttpStatus.NO_CONTENT, responseDelete.getStatusCode());

        ResponseEntity<Pessoa> responseGet = get(PessoaFactory.getUrlGetById(pessoa.getId()), Pessoa.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, responseGet.getStatusCode());
    }

    @Test
    public void delete_with_not_exists_id() {

        ResponseEntity<Void> responsePut = delete(PessoaFactory.getUrlDelete(-1L));

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
