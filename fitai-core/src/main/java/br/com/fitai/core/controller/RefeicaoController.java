package br.com.fitai.core.controller;

import br.com.fitai.core.model.Refeicao;
import br.com.fitai.core.repository.RefeicaoRepository;
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
@RequestMapping("/api/refeicao")
@Slf4j
public class RefeicaoController {

    @Autowired
    RefeicaoRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    private Refeicao getRefeicao(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Refeicao não encontrado"));
    }
    @PostMapping()
    public ResponseEntity<EntityModel<Refeicao>> signup(@RequestBody @Valid Refeicao refeicao, BindingResult result){
        repository.save(refeicao);
        return ResponseEntity
                .created(refeicao.toEntityModel().getRequiredLink("self").toUri())
                .body(refeicao.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<Refeicao> show(@PathVariable Long id){
        var refeicao = getRefeicao(id);
        return refeicao.toEntityModel();
    }

    @GetMapping()
    public PagedModel<EntityModel<Object>> index(@PageableDefault(size = 5) Pageable pageable){ //@RequestParam String busca
        Page<Refeicao> refeicaos = repository.findAll(pageable);
        return assembler.toModel(refeicaos.map(Refeicao::toEntityModel));
    }

    @PutMapping("{id}")
    public EntityModel<Refeicao> update(@PathVariable Long id, @RequestBody @Valid Refeicao refeicao, BindingResult result){
        getRefeicao(id);
        refeicao.setId(id);
        repository.save(refeicao);
        return refeicao.toEntityModel();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Refeicao> destroy(@PathVariable Long id){
        var refeicaoEncontrada = getRefeicao(id);
        repository.delete(refeicaoEncontrada);
        return ResponseEntity.noContent().build();
    }

}
