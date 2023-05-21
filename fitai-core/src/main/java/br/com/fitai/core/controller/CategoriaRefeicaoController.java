package br.com.fitai.core.controller;

import br.com.fitai.core.model.CategoriaRefeicao;
import br.com.fitai.core.repository.CategoriaRefeicaoRepository;
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
@RequestMapping("/api/categoriaRefeicao")
@Slf4j
public class CategoriaRefeicaoController {

    @Autowired
    CategoriaRefeicaoRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    private CategoriaRefeicao getCategoriaRefeicao(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CategoriaRefeicao não encontrado"));
    }
    @PostMapping()
    public ResponseEntity<EntityModel<CategoriaRefeicao>> signup(@RequestBody @Valid CategoriaRefeicao categoriaRefeicao, BindingResult result){
        repository.save(categoriaRefeicao);
        return ResponseEntity
                .created(categoriaRefeicao.toEntityModel().getRequiredLink("self").toUri())
                .body(categoriaRefeicao.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<CategoriaRefeicao> show(@PathVariable Long id){
        var categoriaRefeicao = getCategoriaRefeicao(id);
        return categoriaRefeicao.toEntityModel();
    }

    @GetMapping()
    public PagedModel<EntityModel<Object>> index(@PageableDefault(size = 5) Pageable pageable){ //@RequestParam String busca
        Page<CategoriaRefeicao> categoriaRefeicaos = repository.findAll(pageable);
        return assembler.toModel(categoriaRefeicaos.map(CategoriaRefeicao::toEntityModel));
    }

    @PutMapping("{id}")
    public EntityModel<CategoriaRefeicao> update(@PathVariable Long id, @RequestBody @Valid CategoriaRefeicao categoriaRefeicao, BindingResult result){
        getCategoriaRefeicao(id);
        categoriaRefeicao.setId(id);
        repository.save(categoriaRefeicao);
        return categoriaRefeicao.toEntityModel();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CategoriaRefeicao> destroy(@PathVariable Long id){
        var categoriaRefeicaoEncontrada = getCategoriaRefeicao(id);
        repository.delete(categoriaRefeicaoEncontrada);
        return ResponseEntity.noContent().build();
    }

}
