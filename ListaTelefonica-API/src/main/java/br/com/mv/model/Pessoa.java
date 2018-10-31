package br.com.mv.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="pessoa")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 850601630586643446L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="cpf")
	private String cpf;
	
	@Email(message="Por favor, insira um Email válido")
	@Column(name="email")
	private String email;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name="data_nascimento")
	private Date dataNascimento;
	
	@Valid
	@JsonManagedReference
	@OneToMany(mappedBy = "pessoa", fetch = FetchType.EAGER, targetEntity = Telefone.class, orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Telefone> listTelefone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<Telefone> getListTelefone() {
		return listTelefone;
	}

	public void setListTelefone(List<Telefone> listTelefone) {
		this.listTelefone = listTelefone;
	}
}
