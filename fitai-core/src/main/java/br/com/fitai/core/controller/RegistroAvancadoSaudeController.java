package br.com.fitai.core.controller;

import br.com.fitai.core.model.RegistroAvancadoSaude;
import br.com.fitai.core.repository.RegistroAvancadoSaudeRepository;
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
@RequestMapping("/api/resgistroAvancadoSaude")
@Slf4j
public class RegistroAvancadoSaudeController {

    @Autowired
    RegistroAvancadoSaudeRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    private RegistroAvancadoSaude getRegistroAvancadoSaude(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RegistroAvancadoSaude não encontrado"));
    }
    @PostMapping()
    public ResponseEntity<EntityModel<RegistroAvancadoSaude>> signup(@RequestBody @Valid RegistroAvancadoSaude resgistroAvancadoSaude, BindingResult result){
        repository.save(resgistroAvancadoSaude);
        return ResponseEntity
                .created(resgistroAvancadoSaude.toEntityModel().getRequiredLink("self").toUri())
                .body(resgistroAvancadoSaude.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<RegistroAvancadoSaude> show(@PathVariable Long id){
        var resgistroAvancadoSaude = getRegistroAvancadoSaude(id);
        return resgistroAvancadoSaude.toEntityModel();
    }

    @GetMapping()
    public PagedModel<EntityModel<Object>> index(@PageableDefault(size = 5) Pageable pageable){ //@RequestParam String busca
        Page<RegistroAvancadoSaude> resgistroAvancadoSaudes = repository.findAll(pageable);
        return assembler.toModel(resgistroAvancadoSaudes.map(RegistroAvancadoSaude::toEntityModel));
    }

    @PutMapping("{id}")
    public EntityModel<RegistroAvancadoSaude> update(@PathVariable Long id, @RequestBody @Valid RegistroAvancadoSaude resgistroAvancadoSaude, BindingResult result){
        getRegistroAvancadoSaude(id);
        resgistroAvancadoSaude.setId(id);
        repository.save(resgistroAvancadoSaude);
        return resgistroAvancadoSaude.toEntityModel();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RegistroAvancadoSaude> destroy(@PathVariable Long id){
        var resgistroAvancadoSaudeEncontrada = getRegistroAvancadoSaude(id);
        repository.delete(resgistroAvancadoSaudeEncontrada);
        return ResponseEntity.noContent().build();
    }

}
