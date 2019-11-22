package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorLexico.Categoria;
import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import javafx.scene.control.TreeItem;

/**
 * Clase que describe que es una sentencias ExpresionAritmetica y sus
 * componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class ExpresionAritmetica extends Expresion {
	private ExpresionAritmetica expresionAritmetica;
	private ExpresionAuxiliar expresionAuxiliar;
	private ValorNumerico valorNumerico;

	/**
	 * @param expresionAritmetica
	 * @param expresionAuxiliar
	 */
	public ExpresionAritmetica(ExpresionAritmetica expresionAritmetica, ExpresionAuxiliar expresionAuxiliar) {
		super();
		this.expresionAritmetica = expresionAritmetica;
		this.expresionAuxiliar = expresionAuxiliar;
	}

	/**
	 * @param expresionAritmetica
	 * @param valorNumerico
	 */
	public ExpresionAritmetica(ValorNumerico valorNumerico, ExpresionAuxiliar expresionAuxiliar) {
		super();
		this.expresionAuxiliar = expresionAuxiliar;
		this.valorNumerico = valorNumerico;
	}

	/**
	 * @return the expresionAritmetica
	 */
	public ExpresionAritmetica getExpresionAritmetica() {
		return expresionAritmetica;
	}

	/**
	 * @param expresionAritmetica the expresionAritmetica to set
	 */
	public void setExpresionAritmetica(ExpresionAritmetica expresionAritmetica) {
		this.expresionAritmetica = expresionAritmetica;
	}

	/**
	 * @return the expresionAuxiliar
	 */
	public ExpresionAuxiliar getExpresionAuxiliar() {
		return expresionAuxiliar;
	}

	/**
	 * @param expresionAuxiliar the expresionAuxiliar to set
	 */
	public void setExpresionAuxiliar(ExpresionAuxiliar expresionAuxiliar) {
		this.expresionAuxiliar = expresionAuxiliar;
	}

	/**
	 * @return the valorNumerico
	 */
	public ValorNumerico getValorNumerico() {
		return valorNumerico;
	}

	/**
	 * @param valorNumerico the valorNumerico to set
	 */
	public void setValorNumerico(ValorNumerico valorNumerico) {
		this.valorNumerico = valorNumerico;
	}

	@Override
	public TreeItem<String> getArbolVisual() {

		TreeItem<String> raiz = new TreeItem<String>("Expresion Aritmetica");

		if (getValorNumerico() != null)
			raiz.getChildren().add(valorNumerico.getArbolVisual());

		if (getExpresionAritmetica() != null)
			raiz.getChildren().add(getExpresionAritmetica().getArbolVisual());

		if (getExpresionAuxiliar() != null)
			raiz.getChildren().add(getExpresionAuxiliar().getArbolVisual());

		return raiz;

	}

	@Override
	public String obtenerTipo() {
		return "aritmetica";

	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito,
			String identificador) {

		if (valorNumerico != null) {
			// Me permite saber si se esta analizando una declaracion de variable
			if (identificador != null) {
				Simbolo declaracion = tablaSimbolos.buscarSimboloVariable(identificador, ambito);
				if (declaracion != null) {
					if (valorNumerico.getTermino().getCategoria() == Categoria.IDENTIFICADOR) {
						Simbolo var = tablaSimbolos.buscarSimboloVariable(valorNumerico.getTermino().getPalabra(),
								ambito);
						if (var != null) {
							if (var.getTipo() == declaracion.getTipo()) {
								if (expresionAuxiliar != null) {
									expresionAuxiliar.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito,
											identificador);
								}
							} else {
								erroresSemanticos.add(
										"El tipo de dato de la variable que esta intentando declarar no coincide con su tipo de variable");
							}
						} else {
							erroresSemanticos.add("La variable " + valorNumerico.getTermino().getPalabra()
									+ " no existe ene le ambito actual");
						}
					} else {
						if (declaracion.getTipo().equals("entero")) {
							if (valorNumerico.getTermino().getCategoria() != Categoria.ENTERO) {
								erroresSemanticos.add(
										"El tipo de valor que esta intentando asignar es incompatible con el tipo de dato de la variable");
							}
						} else {
							if (valorNumerico.getTermino().getCategoria() != Categoria.REAL) {
								erroresSemanticos.add(
										"El tipo de valor que esta intentando asignar es incompatible con el tipo de dato de la variable");
							}
						}
					}
				} else {
					erroresSemanticos.add("La variable " + valorNumerico.getTermino().getPalabra()
							+ " no existe ene le ambito actual");
				}
			} else {
				if (valorNumerico.getTermino().getCategoria() == Categoria.IDENTIFICADOR) {
					if (tablaSimbolos.buscarSimboloVariable(valorNumerico.getTermino().getPalabra(), ambito) != null) {
						expresionAuxiliar.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, identificador);
					} else {
						erroresSemanticos.add("La variable " + valorNumerico.getTermino().getPalabra()
								+ " no existe ene le ambito actual");
					}
				}
			}
		} else {
			expresionAritmetica.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, identificador);
		}
	}
}
