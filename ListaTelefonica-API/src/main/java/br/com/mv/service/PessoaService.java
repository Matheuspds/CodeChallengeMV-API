package br.com.mv.service;

import java.util.List;

import br.com.mv.model.Pessoa;

public interface PessoaService {

	Pessoa save(Pessoa pessoa);
	Pessoa buscarPorId(Long id);
	List<Pessoa> findAll();
	Pessoa update(Pessoa pessoa);
	void remove(Pessoa pessoa);
	void deleteTelefone(Long pessoaId, Long telefoneId);
	
}
