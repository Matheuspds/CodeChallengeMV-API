package br.com.mv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mv.model.Pessoa;

@Repository
public interface IPessoaRepository extends JpaRepository<Pessoa, Long>{

}
