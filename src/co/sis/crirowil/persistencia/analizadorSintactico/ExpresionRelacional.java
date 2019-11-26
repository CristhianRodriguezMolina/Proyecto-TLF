package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorLexico.Categoria;
import co.sis.crirowil.persistencia.analizadorLexico.Token;
import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import javafx.scene.control.TreeItem;

public class ExpresionRelacional extends Expresion {

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

	@Override
	public TreeItem<String> getArbolVisual() {

		TreeItem<String> raiz = new TreeItem<String>("Expresion Relacional");

		if (valorLogico == null) {
			if (getExpresionAritmetica() != null)
				raiz.getChildren().add(getExpresionAritmetica().getArbolVisual());

			raiz.getChildren().add(new TreeItem<String>("Operador relacional: " + operadorRelacional.getPalabra()));

			if (getExpresionAritmetica2() != null)
				raiz.getChildren().add(getExpresionAritmetica2().getArbolVisual());
		} else {
			raiz.getChildren().add(new TreeItem<String>("Valor lógico: " + valorLogico.getPalabra()));
		}

		return raiz;

	}

	@Override
	public String obtenerTipo(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		return "bool";
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito,
			String identificador, boolean declaracion) {
		if(identificador == null) 
		{
			Simbolo s = tablaSimbolos.buscarSimboloVariable(identificador, ambito);
			if (s != null) {
				if (!s.getTipo().equals("bool")) {
					erroresSemanticos.add("No se puede convertir de bool a " + s.getTipo());
				} else {
					if (valorLogico == null) {
						expresionAritmetica.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, identificador,
								true);
						expresionAritmetica2.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, identificador,
								true);
					}
					
				}
			} else {
				erroresSemanticos.add("La variable " + identificador + " no existe en el ambito actual");
			}			
		}

	}

	@Override
	public String getJavaCode() {
		String codigo = "";  
		if(valorLogico != null) 
		{
			codigo = valorLogico.getPalabra();
		}
		else 
		{
			codigo += expresionAritmetica.getJavaCode() + operadorRelacional.getPalabra() + expresionAritmetica2.getJavaCode();
		}
		return codigo;
	}

}
