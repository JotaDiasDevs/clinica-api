package br.com.clinica_api.controller;

import br.com.clinica_api.model.Paciente;
import br.com.clinica_api.model.dto.LoginDTO;
import br.com.clinica_api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    // Listar todos os pacientes
    @GetMapping
    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    // Buscar paciente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        // Retorna 200 OK se achar, ou 404 Not Found se não achar
        return paciente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Paciente> criar(@RequestBody Paciente paciente) {
        paciente.setId(null);
        Paciente novoPaciente = pacienteRepository.save(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPaciente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizar(@PathVariable Long id, @RequestBody Paciente pacienteAtualizado) {
        if (!pacienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pacienteAtualizado.setId(id);
        Paciente salvo = pacienteRepository.save(pacienteAtualizado);
        return ResponseEntity.ok(salvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!pacienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pacienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Optional<Paciente> pacienteOpt = pacienteRepository.findByEmail(loginDTO.email());
        
        if (pacienteOpt.isEmpty()) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", "Email não encontrado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
        }
        
        Paciente paciente = pacienteOpt.get();
        
        if (!paciente.getSenha().equals(loginDTO.senha())) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", "Senha incorreta");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
        }
        
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Login realizado com sucesso");
        resposta.put("paciente", paciente);
        return ResponseEntity.ok(resposta);
    }
}