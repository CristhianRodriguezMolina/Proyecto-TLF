package co.sis.crirowil.persistencia.analizadorSintactico;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import javafx.scene.control.TreeItem;

/**
 * 
 * @author Usuario
 *
 */
public class ExpresionAuxiliarLogica {
	
	private Token operadorLogico;
	private ExpresionLogica expresionLogica;
	private ExpresionAuxiliarLogica expresionAuxiliarLogica;
	
	/**
	 * 
	 * @param operadorLogico
	 * @param expresionLogica
	 * @param expresionAuxiliarLogica
	 */
	public ExpresionAuxiliarLogica(Token operadorLogico, ExpresionLogica expresionLogica,
			ExpresionAuxiliarLogica expresionAuxiliarLogica) {
		super();
		this.operadorLogico = operadorLogico;
		this.expresionLogica = expresionLogica;
		this.expresionAuxiliarLogica = expresionAuxiliarLogica;
	}
	
	

	public Token getOperadorLogico() {
		return operadorLogico;
	}



	public void setOperadorLogico(Token operadorLogico) {
		this.operadorLogico = operadorLogico;
	}



	public ExpresionLogica getExpresionLogica() {
		return expresionLogica;
	}



	public void setExpresionLogica(ExpresionLogica expresionLogica) {
		this.expresionLogica = expresionLogica;
	}



	public ExpresionAuxiliarLogica getExpresionAuxiliarLogica() {
		return expresionAuxiliarLogica;
	}



	public void setExpresionAuxiliarLogica(ExpresionAuxiliarLogica expresionAuxiliarLogica) {
		this.expresionAuxiliarLogica = expresionAuxiliarLogica;
	}



	public TreeItem<String> getArbolVisual() {

		TreeItem<String> raiz = new TreeItem<String>("Expresion Auxiliar Logica");
		
		raiz.getChildren().add(new TreeItem<String>("Operador lógico: " + operadorLogico.getPalabra()));
		
		if(getExpresionLogica() != null)
			raiz.getChildren().add(getExpresionLogica().getArbolVisual());
		
		if(getExpresionAuxiliarLogica() != null)
			raiz.getChildren().add(getExpresionAuxiliarLogica().getArbolVisual());
		
		return raiz;
		
	}
}
