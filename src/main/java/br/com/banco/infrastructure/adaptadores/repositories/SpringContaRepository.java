package br.com.banco.infrastructure.adaptadores.repositories;

import br.com.banco.infrastructure.adaptadores.persistence.entities.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SpringContaRepository extends JpaRepository<ContaEntity, Integer> {
    ContaEntity findByNomeResponsavelContainingIgnoreCase(final String nomeResponsavel);
}