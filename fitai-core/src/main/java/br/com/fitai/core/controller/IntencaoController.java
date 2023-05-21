package br.com.fitai.core.controller;

import br.com.fitai.core.model.Intencao;
import br.com.fitai.core.repository.IntencaoRepository;
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
@RequestMapping("/api/intencao")
@Slf4j
public class IntencaoController {

    @Autowired
    IntencaoRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    private Intencao getIntencao(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Intencao não encontrado"));
    }
    @PostMapping()
    public ResponseEntity<EntityModel<Intencao>> signup(@RequestBody @Valid Intencao intencao, BindingResult result){
        repository.save(intencao);
        return ResponseEntity
                .created(intencao.toEntityModel().getRequiredLink("self").toUri())
                .body(intencao.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<Intencao> show(@PathVariable Long id){
        var intencao = getIntencao(id);
        return intencao.toEntityModel();
    }

    @GetMapping()
    public PagedModel<EntityModel<Object>> index(@PageableDefault(size = 5) Pageable pageable){ //@RequestParam String busca
        Page<Intencao> intencaos = repository.findAll(pageable);
        return assembler.toModel(intencaos.map(Intencao::toEntityModel));
    }

    @PutMapping("{id}")
    public EntityModel<Intencao> update(@PathVariable Long id, @RequestBody @Valid Intencao intencao, BindingResult result){
        getIntencao(id);
        intencao.setId(id);
        repository.save(intencao);
        return intencao.toEntityModel();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Intencao> destroy(@PathVariable Long id){
        var intencaoEncontrada = getIntencao(id);
        repository.delete(intencaoEncontrada);
        return ResponseEntity.noContent().build();
    }

}
