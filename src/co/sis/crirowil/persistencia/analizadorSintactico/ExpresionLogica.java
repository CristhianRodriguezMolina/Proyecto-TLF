package co.sis.crirowil.persistencia.analizadorSintactico;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import javafx.scene.control.TreeItem;

public class ExpresionLogica extends Expresion{
	
	private ExpresionLogica expresionLogica;
	private ExpresionRelacional expresionRelacional;
	private ExpresionAuxiliarLogica expresionAuxiliarLogica;
	private boolean negacion;

	/**
	 * 
	 * @param expresionLogica
	 * @param expresionRelacional
	 * @param expresionAuxiliarLogica
	 * @param negacion
	 */
	public ExpresionLogica(ExpresionLogica expresionLogica, ExpresionRelacional expresionRelacional, ExpresionAuxiliarLogica expresionAuxiliarLogica, boolean negacion) {
		super();
		this.expresionLogica = expresionLogica;
		this.expresionRelacional = expresionRelacional;
		this.expresionAuxiliarLogica = expresionAuxiliarLogica;
		this.negacion = negacion;
	}

	public ExpresionLogica getExpresionLogica() {
		return expresionLogica;
	}

	public void setExpresionLogica(ExpresionLogica expresionLogica) {
		this.expresionLogica = expresionLogica;
	}

	public ExpresionRelacional getExpresionRelacional() {
		return expresionRelacional;
	}

	public void setExpresionRelacional(ExpresionRelacional expresionRelacional) {
		this.expresionRelacional = expresionRelacional;
	}

	public ExpresionAuxiliarLogica getExpresionAuxiliarLogica() {
		return expresionAuxiliarLogica;
	}

	public void setExpresionAuxiliarLogica(ExpresionAuxiliarLogica expresionAuxiliarLogica) {
		this.expresionAuxiliarLogica = expresionAuxiliarLogica;
	}

	public boolean getNegacion() {
		return negacion;
	}

	public void setNegacion(boolean negacion) {
		this.negacion = negacion;
	}

	@Override
	public TreeItem<String> getArbolVisual() {

		TreeItem<String> raiz = new TreeItem<String>("Expresion Lógica");
		
		raiz.getChildren().add(new TreeItem<String>("Expresion negada: " + negacion));
		
		raiz.getChildren().add(getExpresionLogica().getArbolVisual());
		
		raiz.getChildren().add(getExpresionRelacional().getArbolVisual());
		
		raiz.getChildren().add(getExpresionAuxiliarLogica().getArbolVisual());
		
		return raiz;
		
	}
	
	
}
