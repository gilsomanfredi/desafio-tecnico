package io.github.gilsomanfredi.cadastropessoa.controller.pessoa.v2;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.github.gilsomanfredi.cadastropessoa.model.pessoa.v2.PessoaV2;
import io.github.gilsomanfredi.cadastropessoa.service.pessoa.v2.PessoaServiceV2;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @GetMapping("/paginado")
    public Page<PessoaV2> findPaginado(Pageable pageable) {

        return pessoaServiceV2.findPaginado(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaV2> findById(@PathVariable Long id) {

        return ResponseEntity.of(pessoaServiceV2.findById(id));
    }

    @PostMapping
    public ResponseEntity<PessoaV2> insert(@Valid @RequestBody PessoaV2 pessoa) {

        pessoaServiceV2.insert(pessoa);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{codigo}")
                .buildAndExpand(pessoa.getId())
                .toUri();

        return ResponseEntity.created(uri).body(pessoa);
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
