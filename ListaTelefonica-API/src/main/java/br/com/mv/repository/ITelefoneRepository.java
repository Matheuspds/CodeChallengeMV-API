package br.com.mv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mv.model.Telefone;

@Repository
public interface ITelefoneRepository extends JpaRepository<Telefone, Long>{

}
