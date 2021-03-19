package com.moises.osservico.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moises.osservico.domain.exception.NegocioException;
import com.moises.osservico.domain.model.Cliente;
import com.moises.osservico.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService {
	@Autowired
	private ClienteRepository clienteRepository;
	//salva um novo cliente, também pode ser usado para atualizar 
	public Cliente salvar(Cliente cliente) {
		Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
		//verifica se já há um cliente cadastrado com o e-mail informado
		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe um cliente cadastrado com este e-mail.");
		}

		return clienteRepository.save(cliente);
	}
 //exclui um cliente
	public void excluir(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}
}
