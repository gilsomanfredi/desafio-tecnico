package io.github.gilsomanfredi.cadastropessoa.controller.pessoa.v2;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.gilsomanfredi.cadastropessoa.model.pessoa.v2.PessoaV2;
import io.github.gilsomanfredi.cadastropessoa.service.pessoa.v2.PessoaServiceV2;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pessoa/v2")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PessoaControllerV2 {

    private final @NonNull
    PessoaServiceV2 pessoaServiceV2;

    @GetMapping
    public List<PessoaV2> findAll() {

        return pessoaServiceV2.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaV2> findById(@PathVariable Long id) {

        return ResponseEntity.of(pessoaServiceV2.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaV2 insert(@Valid @RequestBody PessoaV2 pessoa) {

        return pessoaServiceV2.insert(pessoa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaV2> update(@PathVariable Long id, @Valid @RequestBody PessoaV2 pessoa) {

        return pessoaServiceV2.existsById(id)
                ? ResponseEntity.ok(pessoaServiceV2.update(id, pessoa))
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PessoaV2> delete(@PathVariable Long id) {

        if (pessoaServiceV2.existsById(id)) {
        	pessoaServiceV2.delete(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}
