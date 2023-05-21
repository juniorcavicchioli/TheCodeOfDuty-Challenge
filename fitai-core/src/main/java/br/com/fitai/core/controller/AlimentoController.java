package br.com.fitai.core.controller;

import br.com.fitai.core.model.Alimento;
import br.com.fitai.core.repository.AlimentoRepository;
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
@RequestMapping("/api/alimento/alimento")
@Slf4j
public class AlimentoController {

    @Autowired
    AlimentoRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    private Alimento getAlimento(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alimento não encontrado"));
    }
    @PostMapping()
    public ResponseEntity<EntityModel<Alimento>> signup(@RequestBody @Valid Alimento alimento, BindingResult result){
        repository.save(alimento);
        return ResponseEntity
                .created(alimento.toEntityModel().getRequiredLink("self").toUri())
                .body(alimento.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<Alimento> show(@PathVariable Long id){
        var alimento = getAlimento(id);
        return alimento.toEntityModel();
    }

    @GetMapping()
    public PagedModel<EntityModel<Object>> index(@PageableDefault(size = 5) Pageable pageable){ //@RequestParam String busca
        Page<Alimento> alimentos = repository.findAll(pageable);
        return assembler.toModel(alimentos.map(Alimento::toEntityModel));
    }

    @PutMapping("{id}")
    public EntityModel<Alimento> update(@PathVariable Long id, @RequestBody @Valid Alimento alimento, BindingResult result){
        getAlimento(id);
        alimento.setId(id);
        repository.save(alimento);
        return alimento.toEntityModel();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Alimento> destroy(@PathVariable Long id){
        var alimentoEncontrada = getAlimento(id);
        repository.delete(alimentoEncontrada);
        return ResponseEntity.noContent().build();
    }

}
