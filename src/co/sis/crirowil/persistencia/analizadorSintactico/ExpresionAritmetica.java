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
	public String obtenerTipo(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		if(valorNumerico != null) 
		{
			if(valorNumerico.getTermino().getCategoria() == Categoria.IDENTIFICADOR) 
			{
				Simbolo s = tablaSimbolos.buscarSimboloVariable(valorNumerico.getTermino().getPalabra(), ambito);
				return s.getTipo();
			}
			if(valorNumerico.getTermino().getCategoria() == Categoria.ENTERO) 
			{
				return "entero";
			}
			if(valorNumerico.getTermino().getCategoria() == Categoria.ENTERO) 
			{
				return "real";
			}
		}
		else 
		{
			String tipo = expresionAritmetica.obtenerTipo(tablaSimbolos, erroresSemanticos, ambito);
			if (expresionAuxiliar != null) {
				tipo = expresionAuxiliar.obtenerTipo(tablaSimbolos, erroresSemanticos, ambito);
			}
			return tipo;
		}
		return null;
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito,
			String identificador, boolean relacional) {

		if (valorNumerico != null) {
			// Me permite saber si se esta analizando una declaracion de variable
			if (identificador != null) {
				if (!identificador.equals("/switch")) {
					Simbolo s = tablaSimbolos.buscarSimboloVariable(identificador, ambito);
					if (!relacional) {
						if (s != null) {
							if (valorNumerico.getTermino().getCategoria() == Categoria.IDENTIFICADOR) {
								Simbolo var = tablaSimbolos
										.buscarSimboloVariable(valorNumerico.getTermino().getPalabra(), ambito);
								if (var != null) {
									if (!var.getTipo().equals(s.getTipo())) {
										erroresSemanticos.add(
												"Tipo incorrecto: No se puede convertir de " + var.getTipo() + " a "
														+ s.getTipo() + " en el ambito de " + ambito.getNombre());
									}
								} else {
									erroresSemanticos.add("La variable " + valorNumerico.getTermino().getPalabra()
											+ " no existe ene le ambito " + ambito.getNombre());
								}
							} else {
								if (s.getTipo().equals("entero")) {
									if (valorNumerico.getTermino().getCategoria() != Categoria.ENTERO) {
										erroresSemanticos.add("Tipo incorrecto: No se puede convertir de real a "
												+ s.getTipo() + " en el ambito de " + ambito.getNombre());
									}
								} else {
									if (valorNumerico.getTermino().getCategoria() != Categoria.REAL) {
										erroresSemanticos.add("Tipo incorrecto: No se puede convertir de entero a "
												+ s.getTipo() + " en el ambito de " + ambito.getNombre());
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
								erroresSemanticos
										.add("La variable debe ser de tipo real o entero para poder ser relacionada "
												+ " en el ambito de " + ambito.getNombre());
							}
						}
					}

				}
				if (expresionAuxiliar != null) {
					expresionAuxiliar.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, identificador,
							relacional);
				}
			} else {
				if (!ambito.getNombre().equals("Switch")) {
					if (ambito.getTipo() != null) {

						if (valorNumerico.getTermino().getCategoria() == Categoria.IDENTIFICADOR) {
							Simbolo var = tablaSimbolos.buscarSimboloVariable(valorNumerico.getTermino().getPalabra(),
									ambito);
							if (var != null) {
								if (!var.getTipo().equals(ambito.getTipo())) {
									erroresSemanticos.add("Tipo incorrecto: No se puede convertir de " + var.getTipo()
											+ " a " + ambito.getTipo() + " en el ambito de " + ambito.getNombre());
								}
							} else {
								erroresSemanticos.add("La variable " + valorNumerico.getTermino().getPalabra()
										+ " no existe en el ambito " + ambito.getNombre());
							}
						} else {
							if (ambito.getTipo().equals("entero")) {
								if (valorNumerico.getTermino().getCategoria() != Categoria.ENTERO) {
									erroresSemanticos.add("Tipo incorrecto: No se puede convertir de real a "
											+ ambito.getTipo() + " en el ambito de " + ambito.getNombre());
								}
							} else {
								if (valorNumerico.getTermino().getCategoria() != Categoria.REAL) {
									erroresSemanticos.add("Tipo incorrecto: No se puede convertir de entero a "
											+ ambito.getTipo() + " en el ambito de " + ambito.getNombre());
								}
							}
						}
					} else {
						erroresSemanticos.add("No se puede retornar una expresion en un metodo sin tipo de retorno"
								+ "en el ambito de " + ambito.getNombre());
					}

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

	@Override
	public String getJavaCode() {
		
		if(valorNumerico != null) 
		{
			String temp = valorNumerico.getJavaCode();
			if(expresionAuxiliar != null) 
			{
				temp +=expresionAuxiliar.getJavaCode();
			}
			return temp;
		}
		else 
		{
			String temp = expresionAritmetica.getJavaCode();
			if(expresionAuxiliar != null) 
			{
				temp +=expresionAuxiliar.getJavaCode();
			}
			return temp;
		}
	}
}
