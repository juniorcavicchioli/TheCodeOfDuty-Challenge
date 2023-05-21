package br.com.fitai.core.controller;

import br.com.fitai.core.model.AtividadeFisica;
import br.com.fitai.core.repository.AtividadeFisicaRepository;
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
@RequestMapping("/api/atividadeFisica")
@Slf4j
public class AtividadeFisicaController {

    @Autowired
    AtividadeFisicaRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    private AtividadeFisica getAtividadeFisica(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AtividadeFisica não encontrado"));
    }
    @PostMapping()
    public ResponseEntity<EntityModel<AtividadeFisica>> signup(@RequestBody @Valid AtividadeFisica atividadeFisica, BindingResult result){
        repository.save(atividadeFisica);
        return ResponseEntity
                .created(atividadeFisica.toEntityModel().getRequiredLink("self").toUri())
                .body(atividadeFisica.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<AtividadeFisica> show(@PathVariable Long id){
        var atividadeFisica = getAtividadeFisica(id);
        return atividadeFisica.toEntityModel();
    }

    @GetMapping()
    public PagedModel<EntityModel<Object>> index(@PageableDefault(size = 5) Pageable pageable){ //@RequestParam String busca
        Page<AtividadeFisica> atividadeFisicas = repository.findAll(pageable);
        return assembler.toModel(atividadeFisicas.map(AtividadeFisica::toEntityModel));
    }

    @PutMapping("{id}")
    public EntityModel<AtividadeFisica> update(@PathVariable Long id, @RequestBody @Valid AtividadeFisica atividadeFisica, BindingResult result){
        getAtividadeFisica(id);
        atividadeFisica.setId(id);
        repository.save(atividadeFisica);
        return atividadeFisica.toEntityModel();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AtividadeFisica> destroy(@PathVariable Long id){
        var atividadeFisicaEncontrada = getAtividadeFisica(id);
        repository.delete(atividadeFisicaEncontrada);
        return ResponseEntity.noContent().build();
    }

}
