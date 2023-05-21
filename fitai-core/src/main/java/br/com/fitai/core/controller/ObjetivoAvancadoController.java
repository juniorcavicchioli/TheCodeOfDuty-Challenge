package br.com.fitai.core.controller;

import br.com.fitai.core.model.ObjetivoAvancado;
import br.com.fitai.core.repository.ObjetivoAvancadoRepository;
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
@RequestMapping("/api/objetivoAvancado/objetivoAvancado")
@Slf4j
public class ObjetivoAvancadoController {

    @Autowired
    ObjetivoAvancadoRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    private ObjetivoAvancado getObjetivoAvancado(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ObjetivoAvancado não encontrado"));
    }
    @PostMapping()
    public ResponseEntity<EntityModel<ObjetivoAvancado>> signup(@RequestBody @Valid ObjetivoAvancado objetivoAvancado, BindingResult result){
        repository.save(objetivoAvancado);
        return ResponseEntity
                .created(objetivoAvancado.toEntityModel().getRequiredLink("self").toUri())
                .body(objetivoAvancado.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<ObjetivoAvancado> show(@PathVariable Long id){
        var objetivoAvancado = getObjetivoAvancado(id);
        return objetivoAvancado.toEntityModel();
    }

    @GetMapping()
    public PagedModel<EntityModel<Object>> index(@PageableDefault(size = 5) Pageable pageable){ //@RequestParam String busca
        Page<ObjetivoAvancado> objetivoAvancados = repository.findAll(pageable);
        return assembler.toModel(objetivoAvancados.map(ObjetivoAvancado::toEntityModel));
    }

    @PutMapping("{id}")
    public EntityModel<ObjetivoAvancado> update(@PathVariable Long id, @RequestBody @Valid ObjetivoAvancado objetivoAvancado, BindingResult result){
        getObjetivoAvancado(id);
        objetivoAvancado.setId(id);
        repository.save(objetivoAvancado);
        return objetivoAvancado.toEntityModel();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ObjetivoAvancado> destroy(@PathVariable Long id){
        var objetivoAvancadoEncontrada = getObjetivoAvancado(id);
        repository.delete(objetivoAvancadoEncontrada);
        return ResponseEntity.noContent().build();
    }

}
