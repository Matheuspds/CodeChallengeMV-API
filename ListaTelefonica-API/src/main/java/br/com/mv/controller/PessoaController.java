package br.com.mv.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mv.exceptions.BusinessException;
import br.com.mv.model.Pessoa;
import br.com.mv.service.PessoaService;
import br.com.mv.util.ResponseAPI;
import br.com.mv.util.ResponseAPIUtil;

@RestController
@RequestMapping("api/mv/pessoa")
public class PessoaController {
	
	@Autowired
	PessoaService pessoaService;
	
	@Autowired
	protected ResponseAPIUtil responseAPIUtil;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseAPI> save(@Valid @RequestBody Pessoa pessoa) {
		try {
			return new ResponseEntity<ResponseAPI>(responseAPIUtil.getDefaultSaveResponse(pessoaService.save(pessoa)), HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<ResponseAPI>(responseAPIUtil.mountErrorResponse(e.getRootCause().getLocalizedMessage()), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<ResponseAPI>(responseAPIUtil.getDefaultBusinessExceptionResponse(e), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseAPI>(responseAPIUtil.getDefaultInternalServerErrorResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseAPI> update(@Valid @RequestBody Pessoa pessoa) {
		try {
			return new ResponseEntity<ResponseAPI>(responseAPIUtil.getDefaultUpdateResponse(pessoaService.update(pessoa)), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<ResponseAPI>(responseAPIUtil.getDefaultBusinessExceptionResponse(e), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseAPI>(responseAPIUtil.getDefaultInternalServerErrorResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseAPI> buscarPorId(@PathVariable Long id) {
		try {
			Pessoa pessoa = pessoaService.buscarPorId(id);
			if (pessoa == null) {
				return new ResponseEntity<ResponseAPI>(responseAPIUtil.getDefaultNotFoundResponse(), HttpStatus.OK);
			}
			return new ResponseEntity<ResponseAPI>(responseAPIUtil.mountSuccessResponse(pessoa), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseAPI>(responseAPIUtil.getDefaultInternalServerErrorResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseAPI> findAll() {
		try {
			return new ResponseEntity<ResponseAPI>(responseAPIUtil.mountSuccessResponse(pessoaService.findAll()), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseAPI>(responseAPIUtil.getDefaultExceptionResponse(e), HttpStatus.OK);
		}

	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ResponseAPI> remove(@PathVariable ("id") Long id) {
		try {
			Pessoa pessoa = pessoaService.buscarPorId(id);
			if (pessoa == null) {
				return new ResponseEntity<ResponseAPI>(responseAPIUtil.getDefaultNotFoundResponse(), HttpStatus.OK);
			}
			pessoaService.remove(pessoa);
			return new ResponseEntity<ResponseAPI>(responseAPIUtil.getDefaultDeleteResponse(), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<ResponseAPI>(responseAPIUtil.getDefaultBusinessExceptionResponse(e), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseAPI>(responseAPIUtil.getDefaultInternalServerErrorResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(value="/{pessoaId}/telefone/{telefoneId}")
	public ResponseEntity<ResponseAPI> deleteTelefone(@PathVariable Long pessoaId, @PathVariable Long telefoneId) {
		try {
			Pessoa pessoa = pessoaService.buscarPorId(pessoaId);
			if (pessoa == null) {
				return new ResponseEntity<ResponseAPI>(responseAPIUtil.getDefaultNotFoundResponse(), HttpStatus.OK);
			}
			pessoaService.deleteTelefone(pessoaId, telefoneId);
			return new ResponseEntity<ResponseAPI>(responseAPIUtil.getDefaultDeleteResponse(), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<ResponseAPI>(responseAPIUtil.getDefaultBusinessExceptionResponse(e), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseAPI>(responseAPIUtil.getDefaultInternalServerErrorResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
