package br.com.mv.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;

import br.com.mv.enums.SeverityEnum;
import br.com.mv.util.MessageAPI;
import br.com.mv.util.ResponseAPI;
import br.com.mv.util.ResponseAPIUtil;


@ControllerAdvice
public class RestErrorHandler {

	private MessageSource messageSource;

	@Autowired
	protected ResponseAPIUtil response;

	@Autowired
	public RestErrorHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseAPI handleException(MethodArgumentNotValidException exception) {
		List<MessageAPI> listErros = new ArrayList<>();
		for (String error : exception.getBindingResult().getFieldErrors().stream().map(
				(field) -> field.getField() + " - " + messageSource.getMessage(field, LocaleContextHolder.getLocale()))
				.collect(Collectors.toList())) {
			listErros.add(new MessageAPI(error, SeverityEnum.ERROR));
		}
		return response.mountErrorResponse(listErros);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseAPI handleException(HttpMessageNotReadableException e) {
		e.printStackTrace();
		return response.mountErrorResponse(
				"Ocorreu um erro ao interpretar os dados recebidos pelo serviço. Contate o administrador.");
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseAPI handleException(Exception e) {
		e.printStackTrace();
		return response.getDefaultInternalServerErrorResponse();
	}
}
