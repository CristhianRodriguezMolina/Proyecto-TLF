package co.sis.crirowil.persistencia.analizadorSintactico;

import co.sis.crirowil.persistencia.analizadorLexico.Token;

public class ExpresionAuxiliar {
	
	private Token operadorAritmetico;
	private ExpresionAritmetica expresionAritmetica;
	private ExpresionAuxiliar expresionAuxiliar;
	
	/**
	 * @param operadorAritmetico
	 * @param expresionAritmetica
	 * @param expresionAuxiliar
	 */
	public ExpresionAuxiliar(Token operadorAritmetico, ExpresionAritmetica expresionAritmetica,
			ExpresionAuxiliar expresionAuxiliar) {
		super();
		this.operadorAritmetico = operadorAritmetico;
		this.expresionAritmetica = expresionAritmetica;
		this.expresionAuxiliar = expresionAuxiliar;
	}

	public Token getOperadorAritmetico() {
		return operadorAritmetico;
	}

	public void setOperadorAritmetico(Token operadorAritmetico) {
		this.operadorAritmetico = operadorAritmetico;
	}

	public ExpresionAritmetica getExpresionAritmetica() {
		return expresionAritmetica;
	}

	public void setExpresionAritmetica(ExpresionAritmetica expresionAritmetica) {
		this.expresionAritmetica = expresionAritmetica;
	}

	public ExpresionAuxiliar getExpresionAuxiliar() {
		return expresionAuxiliar;
	}

	public void setExpresionAuxiliar(ExpresionAuxiliar expresionAuxiliar) {
		this.expresionAuxiliar = expresionAuxiliar;
	}
	
	
}
