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
			String identificador, boolean relacional) {

		if (valorNumerico != null) {
			// Me permite saber si se esta analizando una declaracion de variable
			if (identificador != null) {
				Simbolo s = tablaSimbolos.buscarSimboloVariable(identificador, ambito);
				if (!relacional) {
					if (s != null) {
						if (valorNumerico.getTermino().getCategoria() == Categoria.IDENTIFICADOR) {
							Simbolo var = tablaSimbolos.buscarSimboloVariable(valorNumerico.getTermino().getPalabra(),
									ambito);
							if (var != null) {
								if (!var.getTipo().equals(s.getTipo())) {
									System.out.println("3 sad");
									erroresSemanticos.add("Tipo incorrecto: No se puede convertir de " + var.getTipo()
											+ " a " + s.getTipo());
								}
							} else {
								erroresSemanticos.add("La variable " + valorNumerico.getTermino().getPalabra()
										+ " no existe ene le ambito actual");
							}
						} else {
							if (s.getTipo().equals("entero")) {
								if (valorNumerico.getTermino().getCategoria() != Categoria.ENTERO) {
									System.out.println("2 sad");
									erroresSemanticos
											.add("Tipo incorrecto: No se puede convertir de real a " + s.getTipo());
								}
							} else {
								if (valorNumerico.getTermino().getCategoria() != Categoria.REAL) {
									System.out.println("1 sad");
									erroresSemanticos
											.add("Tipo incorrecto: No se puede convertir de entero a " + s.getTipo());
								}
							}
						}
					} else {
						erroresSemanticos.add("La variable " + valorNumerico.getTermino().getPalabra()
								+ " no existe en el ambito actual");
					}
				} else {
					if (valorNumerico.getTermino().getCategoria() == Categoria.IDENTIFICADOR) {
						Simbolo var = tablaSimbolos.buscarSimboloVariable(valorNumerico.getTermino().getPalabra(),
								ambito);
						if (!var.getTipo().equals("entero") && !var.getTipo().equals("real")) {
							erroresSemanticos.add("La variable debe ser de tipo real o entero para poder ser relacionada");
						}
					}
				}
				if (expresionAuxiliar != null) {
					expresionAuxiliar.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, identificador,
							relacional);
				}
			}
		} else {
			expresionAritmetica.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, identificador, relacional);
			if (expresionAuxiliar != null) {
				expresionAuxiliar.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, identificador,
						relacional);
			}
		}
	}
}
