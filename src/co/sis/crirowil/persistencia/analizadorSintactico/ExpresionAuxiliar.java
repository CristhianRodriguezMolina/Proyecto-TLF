package co.sis.crirowil.persistencia.analizadorSintactico;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import javafx.scene.control.TreeItem;

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

	public TreeItem<String> getArbolVisual() {

		TreeItem<String> raiz = new TreeItem<String>("Expresion Auxiliar Aritmetica");
		
		raiz.getChildren().add(new TreeItem<String>("Operador aritmetico: " + operadorAritmetico.getPalabra()));
		
		if(getExpresionAritmetica() != null)
			raiz.getChildren().add(getExpresionAritmetica().getArbolVisual());
		
		if(getExpresionAuxiliar() != null)
			raiz.getChildren().add(getExpresionAuxiliar().getArbolVisual());
		
		return raiz;
		
	}
	
	
}
