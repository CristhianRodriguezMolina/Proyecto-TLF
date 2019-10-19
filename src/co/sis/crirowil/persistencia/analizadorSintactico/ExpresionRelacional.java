package co.sis.crirowil.persistencia.analizadorSintactico;

import co.sis.crirowil.persistencia.analizadorLexico.Token;

public class ExpresionRelacional extends Expresion{

	private ExpresionAritmetica expresionAritmetica, expresionAritmetica2;
	private Token operadorRelacional;
	private Token valorLogico;
	
	/**
	 * 
	 * @param expresionAritmetica
	 * @param expresionAritmetica2
	 * @param operadorRelacional
	 * @param valorLogico
	 */
	public ExpresionRelacional(ExpresionAritmetica expresionAritmetica, ExpresionAritmetica expresionAritmetica2,
			Token operadorRelacional, Token valorLogico) {
		super();
		this.expresionAritmetica = expresionAritmetica;
		this.expresionAritmetica2 = expresionAritmetica2;
		this.operadorRelacional = operadorRelacional;
		this.valorLogico = valorLogico;
	}

	public ExpresionAritmetica getExpresionAritmetica() {
		return expresionAritmetica;
	}

	public void setExpresionAritmetica(ExpresionAritmetica expresionAritmetica) {
		this.expresionAritmetica = expresionAritmetica;
	}

	public ExpresionAritmetica getExpresionAritmetica2() {
		return expresionAritmetica2;
	}

	public void setExpresionAritmetica2(ExpresionAritmetica expresionAritmetica2) {
		this.expresionAritmetica2 = expresionAritmetica2;
	}

	public Token getOperadorRelacional() {
		return operadorRelacional;
	}

	public void setOperadorRelacional(Token operadorRelacional) {
		this.operadorRelacional = operadorRelacional;
	}

	public Token isValorLogico() {
		return valorLogico;
	}

	public void setValorLogico(Token valorLogico) {
		this.valorLogico = valorLogico;
	}
	
	
}
