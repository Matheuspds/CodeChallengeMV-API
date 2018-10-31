package br.com.mv.exceptions;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -3203899760829516032L;

	public BusinessException(String msg) {
		super(msg);
	}
}
