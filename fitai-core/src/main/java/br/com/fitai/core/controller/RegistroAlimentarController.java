package br.com.fitai.core.controller;

import br.com.fitai.core.model.RegistroAlimentar;
import br.com.fitai.core.repository.RegistroAlimentarRepository;
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
@RequestMapping("/api/registroAlimentar")
@Slf4j
public class RegistroAlimentarController {

    @Autowired
    RegistroAlimentarRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    private RegistroAlimentar getRegistroAlimentar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RegistroAlimentar não encontrado"));
    }
    @PostMapping()
    public ResponseEntity<EntityModel<RegistroAlimentar>> signup(@RequestBody @Valid RegistroAlimentar registroAlimentar, BindingResult result){
        repository.save(registroAlimentar);
        return ResponseEntity
                .created(registroAlimentar.toEntityModel().getRequiredLink("self").toUri())
                .body(registroAlimentar.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<RegistroAlimentar> show(@PathVariable Long id){
        var registroAlimentar = getRegistroAlimentar(id);
        return registroAlimentar.toEntityModel();
    }

    @GetMapping()
    public PagedModel<EntityModel<Object>> index(@PageableDefault(size = 5) Pageable pageable){ //@RequestParam String busca
        Page<RegistroAlimentar> registroAlimentars = repository.findAll(pageable);
        return assembler.toModel(registroAlimentars.map(RegistroAlimentar::toEntityModel));
    }

    @PutMapping("{id}")
    public EntityModel<RegistroAlimentar> update(@PathVariable Long id, @RequestBody @Valid RegistroAlimentar registroAlimentar, BindingResult result){
        getRegistroAlimentar(id);
        registroAlimentar.setId(id);
        repository.save(registroAlimentar);
        return registroAlimentar.toEntityModel();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RegistroAlimentar> destroy(@PathVariable Long id){
        var registroAlimentarEncontrada = getRegistroAlimentar(id);
        repository.delete(registroAlimentarEncontrada);
        return ResponseEntity.noContent().build();
    }

}
