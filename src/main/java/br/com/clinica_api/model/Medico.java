package br.com.clinica_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_MEDICO")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String especialidade;
    private String email;
    private String senha;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Consulta> consultas;
}