package br.com.clinica_api.controller;

import br.com.clinica_api.model.Consulta;
import br.com.clinica_api.model.Medico;
import br.com.clinica_api.model.Paciente;
import br.com.clinica_api.model.dto.ConsultaDTO;
import br.com.clinica_api.repository.ConsultaRepository;
import br.com.clinica_api.repository.MedicoRepository;
import br.com.clinica_api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    public ResponseEntity<?> agendarConsulta(@RequestBody ConsultaDTO dto) {
        Medico medico = medicoRepository.findById(dto.medicoId())
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));
        Paciente paciente = pacienteRepository.findById(dto.pacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Consulta novaConsulta = new Consulta();
        novaConsulta.setDataHora(dto.dataHora());
        novaConsulta.setMedico(medico);
        novaConsulta.setPaciente(paciente);

        consultaRepository.save(novaConsulta);

        return ResponseEntity.ok("Consulta agendada com sucesso!");
    }

    @GetMapping
    public List<Consulta> listarTodas() {
        return consultaRepository.findAll();
    }
}