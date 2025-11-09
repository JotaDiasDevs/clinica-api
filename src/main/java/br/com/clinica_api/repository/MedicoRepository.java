package br.com.clinica_api.repository;

import br.com.clinica_api.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
}