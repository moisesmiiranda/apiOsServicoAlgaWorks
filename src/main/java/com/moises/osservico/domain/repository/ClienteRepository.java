package com.moises.osservico.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moises.osservico.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	//consultas
		List<Cliente> findByNome(String nome);
		List<Cliente> findByNomeContaining(String nome);
		Cliente findByEmail(String email);
}
