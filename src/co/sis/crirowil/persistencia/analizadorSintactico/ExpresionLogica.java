package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
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

		TreeItem<String> raiz = new TreeItem<String>("Expresion L�gica");
		
		raiz.getChildren().add(new TreeItem<String>("Expresion negada: " + negacion));
		
		if(getExpresionLogica() != null) {
			raiz.getChildren().add(getExpresionLogica().getArbolVisual());
		}
		
		if(getExpresionRelacional() != null) {
			raiz.getChildren().add(getExpresionRelacional().getArbolVisual());
		}
		
		if(getExpresionAuxiliarLogica() != null) {
			raiz.getChildren().add(getExpresionAuxiliarLogica().getArbolVisual());
		}
		
		return raiz;
		
	}

	@Override
	public String obtenerTipo(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		return "bool";
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito, String identificador, boolean relacional) {
		if(expresionRelacional != null) 
		{
			expresionRelacional.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, identificador, false);
			if(expresionAuxiliarLogica != null) 
			{
				expresionAuxiliarLogica.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, identificador, false);
			}
		}
		else
		{
			expresionLogica.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, identificador, false);
			if(expresionAuxiliarLogica != null) 
			{
				expresionAuxiliarLogica.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, identificador, false);
			}
		}
	}

	@Override
	public String getJavaCode() {
		
		if(expresionRelacional != null) 
		{
			String temp = expresionRelacional.getJavaCode();
			if(expresionAuxiliarLogica != null) 
			{
				temp += expresionAuxiliarLogica.getJavaCode();
			}
			return temp;
		}
		else 
		{
			String temp = "(" + expresionLogica.getJavaCode() + ")";
			if(expresionAuxiliarLogica != null) 
			{
				temp += expresionAuxiliarLogica.getJavaCode();
			}
			return temp;
		}
	}
	
	
}
