package br.com.fitai.core.controller;

import br.com.fitai.core.model.RestricaoAlimentar;
import br.com.fitai.core.repository.RestricaoAlimentarRepository;
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
@RequestMapping("/api/restricaoAlimentar")
@Slf4j
public class RestricaoAlimentarController {

    @Autowired
    RestricaoAlimentarRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    private RestricaoAlimentar getRestricaoAlimentar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RestricaoAlimentar não encontrado"));
    }
    @PostMapping()
    public ResponseEntity<EntityModel<RestricaoAlimentar>> signup(@RequestBody @Valid RestricaoAlimentar restricaoAlimentar, BindingResult result){
        repository.save(restricaoAlimentar);
        return ResponseEntity
                .created(restricaoAlimentar.toEntityModel().getRequiredLink("self").toUri())
                .body(restricaoAlimentar.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<RestricaoAlimentar> show(@PathVariable Long id){
        var restricaoAlimentar = getRestricaoAlimentar(id);
        return restricaoAlimentar.toEntityModel();
    }

    @GetMapping()
    public PagedModel<EntityModel<Object>> index(@PageableDefault(size = 5) Pageable pageable){ //@RequestParam String busca
        Page<RestricaoAlimentar> restricaoAlimentars = repository.findAll(pageable);
        return assembler.toModel(restricaoAlimentars.map(RestricaoAlimentar::toEntityModel));
    }

    @PutMapping("{id}")
    public EntityModel<RestricaoAlimentar> update(@PathVariable Long id, @RequestBody @Valid RestricaoAlimentar restricaoAlimentar, BindingResult result){
        getRestricaoAlimentar(id);
        restricaoAlimentar.setId(id);
        repository.save(restricaoAlimentar);
        return restricaoAlimentar.toEntityModel();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RestricaoAlimentar> destroy(@PathVariable Long id){
        var restricaoAlimentarEncontrada = getRestricaoAlimentar(id);
        repository.delete(restricaoAlimentarEncontrada);
        return ResponseEntity.noContent().build();
    }

}
