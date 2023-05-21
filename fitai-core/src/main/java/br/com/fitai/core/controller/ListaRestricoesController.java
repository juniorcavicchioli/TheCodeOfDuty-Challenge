package br.com.fitai.core.controller;

import br.com.fitai.core.model.ListaRestricoes;
import br.com.fitai.core.repository.ListaRestricoesRepository;
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
@RequestMapping("/api/listaRestricoes")
@Slf4j
public class ListaRestricoesController {

    @Autowired
    ListaRestricoesRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    private ListaRestricoes getListaRestricoes(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ListaRestricoes não encontrado"));
    }
    @PostMapping()
    public ResponseEntity<EntityModel<ListaRestricoes>> signup(@RequestBody @Valid ListaRestricoes listaRestricoes, BindingResult result){
        repository.save(listaRestricoes);
        return ResponseEntity
                .created(listaRestricoes.toEntityModel().getRequiredLink("self").toUri())
                .body(listaRestricoes.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<ListaRestricoes> show(@PathVariable Long id){
        var listaRestricoes = getListaRestricoes(id);
        return listaRestricoes.toEntityModel();
    }

    @GetMapping()
    public PagedModel<EntityModel<Object>> index(@PageableDefault(size = 5) Pageable pageable){ //@RequestParam String busca
        Page<ListaRestricoes> listaRestricoess = repository.findAll(pageable);
        return assembler.toModel(listaRestricoess.map(ListaRestricoes::toEntityModel));
    }

    @PutMapping("{id}")
    public EntityModel<ListaRestricoes> update(@PathVariable Long id, @RequestBody @Valid ListaRestricoes listaRestricoes, BindingResult result){
        getListaRestricoes(id);
        listaRestricoes.setId(id);
        repository.save(listaRestricoes);
        return listaRestricoes.toEntityModel();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ListaRestricoes> destroy(@PathVariable Long id){
        var listaRestricoesEncontrada = getListaRestricoes(id);
        repository.delete(listaRestricoesEncontrada);
        return ResponseEntity.noContent().build();
    }

}
