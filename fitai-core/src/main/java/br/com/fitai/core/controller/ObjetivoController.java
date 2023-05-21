package br.com.fitai.core.controller;

import br.com.fitai.core.model.Objetivo;
import br.com.fitai.core.repository.ObjetivoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/objetivo")
@Slf4j
public class ObjetivoController {

    @Autowired
    ObjetivoRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    private Objetivo getObjetivo(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Objetivo não encontrado"));
    }
    @PostMapping()
    public ResponseEntity<EntityModel<Objetivo>> signup(@RequestBody @Valid Objetivo objetivo, BindingResult result){
        repository.save(objetivo);
        return ResponseEntity
                .created(objetivo.toEntityModel().getRequiredLink("self").toUri())
                .body(objetivo.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<Objetivo> show(@PathVariable Long id){
        var objetivo = getObjetivo(id);
        return objetivo.toEntityModel();
    }

    @GetMapping()
    public PagedModel<EntityModel<Object>> index(@PageableDefault(size = 5) Pageable pageable){ //@RequestParam String busca
        Page<Objetivo> objetivos = repository.findAll(pageable);
        return assembler.toModel(objetivos.map(Objetivo::toEntityModel));
    }

    @PutMapping("{id}")
    public EntityModel<Objetivo> update(@PathVariable Long id, @RequestBody @Valid Objetivo objetivo, BindingResult result){
        getObjetivo(id);
        objetivo.setId(id);
        repository.save(objetivo);
        return objetivo.toEntityModel();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Objetivo> destroy(@PathVariable Long id){
        var objetivoEncontrada = getObjetivo(id);
        repository.delete(objetivoEncontrada);
        return ResponseEntity.noContent().build();
    }

}
