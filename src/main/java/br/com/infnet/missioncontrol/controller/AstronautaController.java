package br.com.infnet.missioncontrol.controller;

import br.com.infnet.missioncontrol.model.Astronauta;
import br.com.infnet.missioncontrol.model.ErrorResponse;
import br.com.infnet.missioncontrol.model.Missao;
import br.com.infnet.missioncontrol.model.StatusAstronauta;
import br.com.infnet.missioncontrol.service.AstronautaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/astronautas")
public class AstronautaController {

    private final AstronautaService astronautaService;

    public AstronautaController(AstronautaService astronautaService) {
        this.astronautaService = astronautaService;
    }

    @GetMapping
    public List<Astronauta> listar(@RequestParam(required = false)StatusAstronauta status) {
        if (status != null){
            return astronautaService.filtrarPorStatus(status);
        } else {
            return astronautaService.listarTodos();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarPeloId(@PathVariable Long id){
        if(id <=0){
            return ResponseEntity.badRequest().body(new ErrorResponse("ID inválido"));
        }

        Astronauta astronauta = astronautaService.buscarPeloId(id);

        if (astronauta != null){
            return ResponseEntity.ok().body(astronauta);
        } else {
            return ResponseEntity.status(404).body(new ErrorResponse("Astronauta não localizado"));
        }
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Astronauta astronauta) {
        try {
            astronautaService.adicionar(astronauta);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(astronauta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        if(id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("ID invalido."));
        }
        boolean removeu = astronautaService.remover(id);
        if(removeu) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Id não localizado."));
    }

    @PutMapping("/{id}")
    public  ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @RequestBody Astronauta novosDados){

        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("ID inválido"));
        }

        try {
            Astronauta astronautaAtualizado = astronautaService.atualizar(id, novosDados);
            if (astronautaAtualizado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Id não localizado"));
            }
            return ResponseEntity.ok(astronautaAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(e.getMessage()));
        }
    }







}
