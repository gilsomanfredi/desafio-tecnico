package io.github.gilsomanfredi.cadastropessoa.controller.source;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/source")
public class SourceController {

    @GetMapping
    public String source() {

        return "https://github.com/gilsomanfredi/desafio-tecnico";
    }

}
