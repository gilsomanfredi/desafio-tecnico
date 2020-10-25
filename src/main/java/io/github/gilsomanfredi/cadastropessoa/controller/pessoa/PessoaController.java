package io.github.gilsomanfredi.cadastropessoa.controller.pessoa;

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

import io.github.gilsomanfredi.cadastropessoa.model.pessoa.Pessoa;
import io.github.gilsomanfredi.cadastropessoa.service.pessoa.PessoaService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pessoa")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PessoaController {

    private final @NonNull
    PessoaService pessoaService;

    @GetMapping
    public List<Pessoa> findAll() {

        return pessoaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> findById(@PathVariable Long id) {

        return ResponseEntity.of(pessoaService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa insert(@Valid @RequestBody Pessoa pessoa) {

        return pessoaService.insert(pessoa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa) {

        return pessoaService.existsById(id)
                ? ResponseEntity.ok(pessoaService.update(id, pessoa))
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pessoa> delete(@PathVariable Long id) {

        if (pessoaService.existsById(id)) {
            pessoaService.delete(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}
