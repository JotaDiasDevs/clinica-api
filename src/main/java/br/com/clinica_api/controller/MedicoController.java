package br.com.clinica_api.controller;

import br.com.clinica_api.model.Medico;
import br.com.clinica_api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @GetMapping
    public List<Medico> listarTodos() {
        return medicoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> buscarPorId(@PathVariable Long id) {
        Optional<Medico> medico = medicoRepository.findById(id);
        return medico.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Medico> criar(@RequestBody Medico medico) {
        medico.setId(null);
        Medico novoMedico = medicoRepository.save(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMedico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medico> atualizar(@PathVariable Long id, @RequestBody Medico medicoAtualizado) {
        if (!medicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        medicoAtualizado.setId(id);
        Medico salvo = medicoRepository.save(medicoAtualizado);
        return ResponseEntity.ok(salvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!medicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        medicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}