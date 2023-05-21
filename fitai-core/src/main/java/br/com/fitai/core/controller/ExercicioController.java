package br.com.fitai.core.controller;

import br.com.fitai.core.model.Exercicio;
import br.com.fitai.core.repository.ExercicioRepository;
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
@RequestMapping("/api/exercicio")
@Slf4j
public class ExercicioController {

    @Autowired
    ExercicioRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    private Exercicio getExercicio(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercicio não encontrado"));
    }
    @PostMapping()
    public ResponseEntity<EntityModel<Exercicio>> signup(@RequestBody @Valid Exercicio exercicio, BindingResult result){
        repository.save(exercicio);
        return ResponseEntity
                .created(exercicio.toEntityModel().getRequiredLink("self").toUri())
                .body(exercicio.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<Exercicio> show(@PathVariable Long id){
        var exercicio = getExercicio(id);
        return exercicio.toEntityModel();
    }

    @GetMapping()
    public PagedModel<EntityModel<Object>> index(@PageableDefault(size = 5) Pageable pageable){ //@RequestParam String busca
        Page<Exercicio> exercicios = repository.findAll(pageable);
        return assembler.toModel(exercicios.map(Exercicio::toEntityModel));
    }

    @PutMapping("{id}")
    public EntityModel<Exercicio> update(@PathVariable Long id, @RequestBody @Valid Exercicio exercicio, BindingResult result){
        getExercicio(id);
        exercicio.setId(id);
        repository.save(exercicio);
        return exercicio.toEntityModel();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Exercicio> destroy(@PathVariable Long id){
        var exercicioEncontrada = getExercicio(id);
        repository.delete(exercicioEncontrada);
        return ResponseEntity.noContent().build();
    }

}
