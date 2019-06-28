package co.sis.crirowil.persistencia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnalizadorLexico {

	private String codigoFuente;
	private ArrayList<Token> listaTokens;
	private String[] palabrasReservadas = { "ciclo", "metodo", "cadena", "entero", "real", "devolver", "importar" };
	private char caracterActual, finCodigo;
	private int posActual, colActual, filaActual;

	public AnalizadorLexico(String codigoFuente) {
		this.codigoFuente = codigoFuente;
		this.listaTokens = new ArrayList<Token>();
		this.caracterActual = codigoFuente.charAt(posActual);
		this.finCodigo = 0;
	}

	public void analizar() {

		while (caracterActual != finCodigo) {

			if (caracterActual == ' ' || caracterActual == '\n' || caracterActual == '\t') {
				obtenerSgteCaracter();
				continue;
			}

			if (esLlaves())
				continue;
			if (esParentesis())
				continue;
			if (esOperadorLogico())
				continue;
			if (esReal())
				continue;
			if (esNatural())
				continue;
			if (esOperadorAsignacion())
				continue;
			if (esOperadorAritmetico())
				continue;
			if (esPalabraReservada())
				continue;
			if (esIdentificador())
				continue;

			listaTokens.add(new Token(Categoria.DESCONOCIDO, "" + caracterActual, filaActual, colActual));
			obtenerSgteCaracter();
		}

		Collections.sort((List<Token>) listaTokens);

	}

	public boolean contenidoArregloReservadas(String palabra) {
		for (int i = 0; i < palabrasReservadas.length; i++) {
			if (palabrasReservadas[i].equals(palabra)) {
				return true;
			}
		}
		return false;
	}

	public boolean esPalabraReservada() {

		if (Character.isLetter(caracterActual)) {
			String palabra = "";
			char caracterActualTemp = caracterActual;
			int fila = filaActual;
			int columna = colActual;

			// Transición
			palabra += caracterActual;
			obtenerSgteCaracter();

			boolean flag = true;
			while (Character.isLetter(caracterActual) && flag == true) {
				palabra += caracterActual;
				obtenerSgteCaracter();

				if (contenidoArregloReservadas(palabra)) {
					break;
				}
			}

			if (contenidoArregloReservadas(palabra)) {
				listaTokens.add(new Token(Categoria.PALABRA_RESERVADA, palabra, fila, columna));
			} else {
				caracterActual = caracterActualTemp;
				filaActual = fila;
				colActual = columna;

				return false;
			}

			return true;
		}

		// RI
		return false;
	}

	public boolean esFinSentencia() {

		if (caracterActual == '@') {
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;

			// Transición
			palabra += caracterActual;
			obtenerSgteCaracter();

			listaTokens.add(new Token(Categoria.FIN_SENTENCIA, palabra, fila, columna));
			return true;

		}

		// RI
		return false;
	}

	public boolean esLlaves() {

		if (caracterActual == '{' || caracterActual == '}') {
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;

			// Transición
			palabra += caracterActual;
			obtenerSgteCaracter();

			if (palabra.equals("{")) {
				listaTokens.add(new Token(Categoria.LLAVES_ABRE, palabra, fila, columna));
			} else {
				listaTokens.add(new Token(Categoria.LLAVES_CIERRA, palabra, fila, columna));
			}

			return true;

		}

		// RI
		return false;
	}

	public boolean esParentesis() {

		if (caracterActual == '(' || caracterActual == ')') {
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;

			// Transición
			palabra += caracterActual;
			obtenerSgteCaracter();

			if (palabra.equals("(")) {
				listaTokens.add(new Token(Categoria.PARENTESIS_ABRE, palabra, fila, columna));
			} else {
				listaTokens.add(new Token(Categoria.PARENTESIS_CIERRA, palabra, fila, columna));
			}

			return true;

		}

		// RI
		return false;
	}

	public boolean esTerminal() {

		if (caracterActual == '@') {
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;

			// Transición
			palabra += caracterActual;
			obtenerSgteCaracter();

			listaTokens.add(new Token(Categoria.FIN_SENTENCIA, palabra, fila, columna));
			return true;

		}

		// RI
		return false;
	}

	public boolean esSeparador() {

		if (caracterActual == ',') {
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;

			// Transición
			palabra += caracterActual;
			obtenerSgteCaracter();

			listaTokens.add(new Token(Categoria.SEPARADOR, palabra, fila, columna));
			return true;

		}

		// RI
		return false;
	}

	public boolean esOperadorAsignacion() {

		if (caracterActual == '+' || caracterActual == '=' || caracterActual == '*' || caracterActual == '/'
				|| caracterActual == '%' || caracterActual == '^' || caracterActual == '-') {
			String palabra = "";
			char caracterActualTemp = caracterActual;
			int fila = filaActual;
			int columna = colActual;

			// Transición
			palabra += caracterActual;
			obtenerSgteCaracter();

			if (palabra.equals(caracterActual + "") && (palabra.equals("+") || palabra.equals("-"))) {
				palabra += caracterActual;
				obtenerSgteCaracter();

				listaTokens.add(new Token(Categoria.OPERADOR_ASIGNACION, palabra, fila, columna));
				return true;
			} else if (!palabra.equals("=")) {
				caracterActual = caracterActualTemp;
				filaActual = fila;
				colActual = columna;

				return false;
			}

			listaTokens.add(new Token(Categoria.OPERADOR_ASIGNACION, palabra, fila, columna));
			return true;

		}

		// RI
		return false;
	}

	public boolean esOperadorAritmetico() {

		if (caracterActual == '+' || caracterActual == '-' || caracterActual == '*' || caracterActual == '/'
				|| caracterActual == '%' || caracterActual == '^') {
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;

			// Transición
			palabra += caracterActual;
			obtenerSgteCaracter();

			listaTokens.add(new Token(Categoria.OPERADOR_ARITMETICO, palabra, fila, columna));
			return true;

		}

		// RI
		return false;
	}

	public boolean esReal() {

		if (Character.isDigit(caracterActual) || caracterActual == '.') {
			String palabra = "";
			char caracterActualTemp = caracterActual;
			int fila = filaActual;
			int columna = colActual;

			// Transición
			palabra += caracterActual;
			obtenerSgteCaracter();

			if (palabra.equals(".") && Character.isDigit(caracterActual)) {
				while (Character.isDigit(caracterActual)) {
					palabra += caracterActual;
					obtenerSgteCaracter();
				}
			} else if (Character.isDigit(palabra.charAt(0))) {
				// Este flag verifica que ya se haya pasado una vez por un punto ('.'), ya que
				// obivamente un real solo tiene un punto
				boolean flag = false;

				while (Character.isDigit(caracterActual) || (caracterActual == '.' && !flag)) {
					if (caracterActual == '.') {
						flag = true;
					}

					palabra += caracterActual;
					obtenerSgteCaracter();
				}
			}

			if (palabra.contains(".") && palabra.length() > 1) {
				listaTokens.add(new Token(Categoria.REAL, palabra, fila, columna));
			} else {
				caracterActual = caracterActualTemp;
				filaActual = fila;
				colActual = columna;

				return false;
			}

			return true;

		}

		// RI
		return false;
	}

	public boolean esOperadorLogico() {
		if (caracterActual == 'y' || caracterActual == 'o') {
			String palabra = "";
			char caracterActualTemp = caracterActual;
			int fila = filaActual;
			int columna = colActual;

			palabra += caracterActual;
			obtenerSgteCaracter();
			if (palabra.equals(caracterActual + "")) {
				palabra += caracterActual;
				obtenerSgteCaracter();
			} else {
				caracterActual = caracterActualTemp;
				filaActual = fila;
				colActual = columna;

				return false;
			}

			listaTokens.add(new Token(Categoria.OPERADOR_LOGICO, palabra, fila, columna));
			return true;
		}
//		else if (caracterActual == 'o') {
//			String palabra = "";
//			int fila = filaActual;
//			int columna = colActual;
//
//			palabra += caracterActual;
//			obtenerSgteCaracter();
//			if (caracterActual == 'o') {
//				palabra += caracterActual;
//				obtenerSgteCaracter();
//				listaTokens.add(new Token(Categoria.OPERADOR_LOGICO, palabra, fila, columna));
//				return true;
//			} else {
//				return false;
//			}
//
//		}

		// RI
		return false;
	}

	public boolean esNatural() {

		if (Character.isDigit(caracterActual)) {
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;

			// Transición
			palabra += caracterActual;
			obtenerSgteCaracter();

			while (Character.isDigit(caracterActual)) {
				palabra += caracterActual;
				obtenerSgteCaracter();
			}

			listaTokens.add(new Token(Categoria.ENTERO, palabra, fila, columna));
			return true;

		}

		// RI
		return false;
	}

	public boolean esIdentificador() {

		if (Character.isLetter(caracterActual) || caracterActual == '_' || caracterActual == '$') {
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;

			// Transición
			palabra += caracterActual;
			obtenerSgteCaracter();

			while (Character.isLetter(caracterActual) || caracterActual == '_' || caracterActual == '$'
					|| Character.isDigit(caracterActual)) {
				palabra += caracterActual;
				obtenerSgteCaracter();
			}

			listaTokens.add(new Token(Categoria.IDENTIFICADOR, palabra, fila, columna));
			return true;

		}

		// RI
		return false;
	}

	public void obtenerSgteCaracter() {

		posActual++;

		if (posActual < codigoFuente.length()) {

			if (caracterActual == '\n') {
				filaActual++;
				colActual = 0;
			} else {
				colActual++;
			}

			caracterActual = codigoFuente.charAt(posActual);
		} else {
			caracterActual = finCodigo;
		}

	}

	public void obtenerAntCaracter() {

		posActual--;

		if (posActual > 0) {

			colActual--;

			caracterActual = codigoFuente.charAt(posActual);
		} else {
			caracterActual = codigoFuente.charAt(0);
		}

	}

	public ArrayList<Token> getListaTokens() {
		return listaTokens;
	}

}
