package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import javafx.scene.control.TreeItem;

public class ExpresionAuxiliar extends Expresion{
	
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
	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito, String identificador, boolean declaracion) 
	{
		expresionAritmetica.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, identificador, declaracion);
		if(expresionAuxiliar != null) 
		{
			expresionAuxiliar.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, identificador, declaracion);			
		}
	}

	@Override
	public String obtenerTipo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
