package com.moises.osservico.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moises.osservico.api.model.Comentario;
import com.moises.osservico.domain.exception.EntidadeNaoEncontradaException;
import com.moises.osservico.domain.exception.NegocioException;
import com.moises.osservico.domain.model.Cliente;
import com.moises.osservico.domain.model.OrdemServico;
import com.moises.osservico.domain.model.StatusOrdemServico;
import com.moises.osservico.domain.repository.ClienteRepository;
import com.moises.osservico.domain.repository.ComentarioRepository;
import com.moises.osservico.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {

	//injetando dependencias
	@Autowired 
	private OrdemServicoRepository ordemServicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ComentarioRepository comentarioRepository;

	//Ordem de serviço está ligada ao cliente
	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));

		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);//Define o status como aberta
		ordemServico.setDataAbertura(OffsetDateTime.now());//Pega data atual

		return ordemServicoRepository.save(ordemServico);
	}

	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);

		ordemServico.finalizar();

		ordemServicoRepository.save(ordemServico);
	}

	//um comentário está ligado a uma ordem de serviço
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);

		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);

		return comentarioRepository.save(comentario);
	}
	//Encontra uma ordem de serviço pelo ID
	private OrdemServico buscar(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
	}
}
