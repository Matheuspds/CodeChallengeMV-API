package br.com.mv.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mv.exceptions.BusinessException;
import br.com.mv.model.Pessoa;
import br.com.mv.model.Telefone;
import br.com.mv.repository.IPessoaRepository;
import br.com.mv.repository.ITelefoneRepository;
import br.com.mv.service.PessoaService;

@Service
@Transactional
public class PessoaServiceImpl implements PessoaService {

	@Autowired
    private IPessoaRepository pessoaRepository;
    @Autowired
    private ITelefoneRepository telefoneRepository;
    
    @Override
    public Pessoa save(Pessoa pessoa) {
		return this.pessoaRepository.save(pessoa);
	}
    
    @Override
    public Pessoa buscarPorId(Long id) {
		return pessoaRepository.findById(id).orElseThrow(() -> new BusinessException("Pessoa não foi encontrada. Tente Novamente!"));
    }
    
    @Override
    public void remove(Pessoa pessoa) {
        pessoaRepository.delete(pessoa);
    }
    
    @Override
    public List<Pessoa> findAll() {
        return this.pessoaRepository.findAll();
    }
    
    @Override
	public Pessoa update(Pessoa pessoa) {
		List<Telefone> telefones = new ArrayList<>();
		for(Telefone telefone: pessoa.getListTelefone()) {
			if(telefone.getId() == null) telefones.add(this.telefoneRepository.save(telefone));
			else telefones.add(telefone);
		}
		pessoa.setListTelefone(telefones);
		return this.pessoaRepository.save(pessoa);
	}
    
    @Override
	public void deleteTelefone(Long pessoaId, Long telefoneId) {
		Pessoa pessoa  = buscarPorId(pessoaId);
		Telefone telefone = this.telefoneRepository.findById(telefoneId).orElse(null);
		if(pessoa.getListTelefone().remove(telefone)) {
			this.pessoaRepository.save(pessoa);
		}
	}
   
}
