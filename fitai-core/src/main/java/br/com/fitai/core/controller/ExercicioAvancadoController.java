package br.com.fitai.core.controller;

import br.com.fitai.core.model.ExercicioAvancado;
import br.com.fitai.core.repository.ExercicioAvancadoRepository;
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
@RequestMapping("/api/exercicioAvancado")
@Slf4j
public class ExercicioAvancadoController {

    @Autowired
    ExercicioAvancadoRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    private ExercicioAvancado getExercicioAvancado(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ExercicioAvancado não encontrado"));
    }
    @PostMapping()
    public ResponseEntity<EntityModel<ExercicioAvancado>> signup(@RequestBody @Valid ExercicioAvancado exercicioAvancado, BindingResult result){
        repository.save(exercicioAvancado);
        return ResponseEntity
                .created(exercicioAvancado.toEntityModel().getRequiredLink("self").toUri())
                .body(exercicioAvancado.toEntityModel());
    }

    @GetMapping("{id}")
    public EntityModel<ExercicioAvancado> show(@PathVariable Long id){
        var exercicioAvancado = getExercicioAvancado(id);
        return exercicioAvancado.toEntityModel();
    }

    @GetMapping()
    public PagedModel<EntityModel<Object>> index(@PageableDefault(size = 5) Pageable pageable){ //@RequestParam String busca
        Page<ExercicioAvancado> exercicioAvancados = repository.findAll(pageable);
        return assembler.toModel(exercicioAvancados.map(ExercicioAvancado::toEntityModel));
    }

    @PutMapping("{id}")
    public EntityModel<ExercicioAvancado> update(@PathVariable Long id, @RequestBody @Valid ExercicioAvancado exercicioAvancado, BindingResult result){
        getExercicioAvancado(id);
        exercicioAvancado.setId(id);
        repository.save(exercicioAvancado);
        return exercicioAvancado.toEntityModel();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ExercicioAvancado> destroy(@PathVariable Long id){
        var exercicioAvancadoEncontrada = getExercicioAvancado(id);
        repository.delete(exercicioAvancadoEncontrada);
        return ResponseEntity.noContent().build();
    }

}
