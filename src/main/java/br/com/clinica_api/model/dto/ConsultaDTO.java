package br.com.clinica_api.model.dto;

import java.time.LocalDateTime;

public record ConsultaDTO(Long medicoId, Long pacienteId, LocalDateTime dataHora) {}