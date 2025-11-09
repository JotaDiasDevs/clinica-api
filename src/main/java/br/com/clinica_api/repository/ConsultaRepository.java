package br.com.clinica_api.repository;

import br.com.clinica_api.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

}
