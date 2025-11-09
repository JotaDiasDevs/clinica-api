package br.com.clinica_api.controller;

import br.com.clinica_api.model.Medico;
import br.com.clinica_api.model.dto.LoginDTO;
import br.com.clinica_api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Optional<Medico> medicoOpt = medicoRepository.findByEmail(loginDTO.email());
        
        if (medicoOpt.isEmpty()) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", "Email não encontrado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
        }
        
        Medico medico = medicoOpt.get();
        
        if (!medico.getSenha().equals(loginDTO.senha())) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", "Senha incorreta");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
        }
        
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Login realizado com sucesso");
        resposta.put("medico", medico);
        return ResponseEntity.ok(resposta);
    }
}