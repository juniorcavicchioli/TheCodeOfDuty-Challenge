package br.com.fitai.core.controller;

import br.com.fitai.core.model.CategoriaExercicio;
import br.com.fitai.core.repository.CategoriaExercicioRepository;
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
@RequestMapping("/api/categoriaExercicio/categoriaExercicio")
@Slf4j
public class CategoriaExercicioController {

    @Autowired
    CategoriaExercicioRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    private CategoriaExercicio getCategoriaExercicio(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CategoriaExercicio não encontrado"));
    }
    @PostMapping()
    public ResponseEntity<EntityModel<CategoriaExercicio>> signup(@RequestBody @Valid CategoriaExercicio categoriaExercicio, BindingResult result){
        repository.save(categoriaExercicio);
        return ResponseEntity
                .created(categoriaExercicio.toEntityModel().getRequiredLink("self").toUri())
                .body(categoriaExercicio.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<CategoriaExercicio> show(@PathVariable Long id){
        var categoriaExercicio = getCategoriaExercicio(id);
        return categoriaExercicio.toEntityModel();
    }

    @GetMapping()
    public PagedModel<EntityModel<Object>> index(@PageableDefault(size = 5) Pageable pageable){ //@RequestParam String busca
        Page<CategoriaExercicio> categoriaExercicios = repository.findAll(pageable);
        return assembler.toModel(categoriaExercicios.map(CategoriaExercicio::toEntityModel));
    }

    @PutMapping("{id}")
    public EntityModel<CategoriaExercicio> update(@PathVariable Long id, @RequestBody @Valid CategoriaExercicio categoriaExercicio, BindingResult result){
        getCategoriaExercicio(id);
        categoriaExercicio.setId(id);
        repository.save(categoriaExercicio);
        return categoriaExercicio.toEntityModel();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CategoriaExercicio> destroy(@PathVariable Long id){
        var categoriaExercicioEncontrada = getCategoriaExercicio(id);
        repository.delete(categoriaExercicioEncontrada);
        return ResponseEntity.noContent().build();
    }

}
