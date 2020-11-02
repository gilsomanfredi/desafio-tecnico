package io.github.gilsomanfredi.cadastropessoa.controller.pessoa.v1;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.github.gilsomanfredi.cadastropessoa.model.pessoa.v1.Pessoa;
import io.github.gilsomanfredi.cadastropessoa.service.pessoa.v1.PessoaService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @GetMapping("/paginado")
    public Page<Pessoa> findPaginado(Pageable pageable) {

        return pessoaService.findPaginado(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> findById(@PathVariable Long id) {

        return ResponseEntity.of(pessoaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Pessoa> insert(@Valid @RequestBody Pessoa pessoa) {

        pessoaService.insert(pessoa);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{codigo}")
                .buildAndExpand(pessoa.getId())
                .toUri();

        return ResponseEntity.created(uri).body(pessoa);
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
