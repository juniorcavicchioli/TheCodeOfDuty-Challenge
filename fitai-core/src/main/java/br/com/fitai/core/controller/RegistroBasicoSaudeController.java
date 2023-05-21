package br.com.fitai.core.controller;

import br.com.fitai.core.model.RegistroBasicoSaude;
import br.com.fitai.core.repository.RegistroBasicoSaudeRepository;
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
@RequestMapping("/api/registroBasicoSaude")
@Slf4j
public class RegistroBasicoSaudeController {

    @Autowired
    RegistroBasicoSaudeRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    private RegistroBasicoSaude getRegistroBasicoSaude(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RegistroBasicoSaude não encontrado"));
    }
    @PostMapping()
    public ResponseEntity<EntityModel<RegistroBasicoSaude>> signup(@RequestBody @Valid RegistroBasicoSaude registroBasicoSaude, BindingResult result){
        repository.save(registroBasicoSaude);
        return ResponseEntity
                .created(registroBasicoSaude.toEntityModel().getRequiredLink("self").toUri())
                .body(registroBasicoSaude.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<RegistroBasicoSaude> show(@PathVariable Long id){
        var registroBasicoSaude = getRegistroBasicoSaude(id);
        return registroBasicoSaude.toEntityModel();
    }

    @GetMapping()
    public PagedModel<EntityModel<Object>> index(@PageableDefault(size = 5) Pageable pageable){ //@RequestParam String busca
        Page<RegistroBasicoSaude> registroBasicoSaudes = repository.findAll(pageable);
        return assembler.toModel(registroBasicoSaudes.map(RegistroBasicoSaude::toEntityModel));
    }

    @PutMapping("{id}")
    public EntityModel<RegistroBasicoSaude> update(@PathVariable Long id, @RequestBody @Valid RegistroBasicoSaude registroBasicoSaude, BindingResult result){
        getRegistroBasicoSaude(id);
        registroBasicoSaude.setId(id);
        repository.save(registroBasicoSaude);
        return registroBasicoSaude.toEntityModel();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RegistroBasicoSaude> destroy(@PathVariable Long id){
        var registroBasicoSaudeEncontrada = getRegistroBasicoSaude(id);
        repository.delete(registroBasicoSaudeEncontrada);
        return ResponseEntity.noContent().build();
    }

}
