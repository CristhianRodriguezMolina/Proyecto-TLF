package co.sis.crirowil.persistencia.analizadorLexico;

import java.util.ArrayList;

/**
 * Clase que analiza los tokens del lenguaje de programacion dado un codigo
 * fuente
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class AnalizadorLexico {

	/**
	 * Cadena de textos donde se van a identificar los tokens
	 */
	private String codigoFuente;

	/**
	 * Guarda los tokens ya analizados
	 */
	private ArrayList<Token> listaTokens;

	/**
	 * Arreglo que guarda las palabras reservadas del lenguaje
	 */
	private String[] palabrasReservadas = { "ciclo", "metodo", "cadena", "entero", "real", "devolver", "importar",
			"sisas", "nonas", "nada" , "nonais", "bool", "char", "imprimir", "leer", "true", "false"};

	/**
	 * Variable que guarda el caracter que esta Actualmente en revision
	 */
	private char caracterActual;
	private char finCodigo;

	/**
	 * Variable que guarda la posicion del caracterActual
	 */
	private int posActual;

	/**
	 * Variables que guardan la fila y columna del token encontrado
	 */
	private int colActual, filaActual;
	
	/**
	 * Lista que guarda los errores lexicos
	 */
	private ArrayList<ErrorLexico> listaErrores;

	/**
	 * Constructor
	 * 
	 * @param codigoFuente
	 */
	public AnalizadorLexico(String codigoFuente) {
		this.codigoFuente = codigoFuente;
		this.listaTokens = new ArrayList<Token>();
		this.listaErrores = new ArrayList<ErrorLexico>();
		this.caracterActual = codigoFuente.charAt(posActual);
		this.finCodigo = 0;
		this.filaActual = 1;
	}

	/**
	 * Metodo que analiza el codigo fuente y define el tipo de los token segun con
	 * las categorias que cuenta el lenguaje
	 * @throws ErrorLexico 
	 */
	public void analizar() throws ErrorLexico {

		while (caracterActual != finCodigo) {

			// Desprecia saltos de linea y tabulaciones
			if (caracterActual == ' ' || caracterActual == '\n' || caracterActual == '\t' || caracterActual == '\r') {
				obtenerSgteCaracter();
				continue;
			}

			if (esComentarioBloque())
				continue;
			if (esComentarioLinea())
				continue;
			if (esHexadecimal())
				continue;
			if (esTerminal())
				continue;
			if (esSeparador())
				continue;
			if (esReal())
				continue;
			if (esPunto())
				continue;
			if (esDosPuntos())
				continue;
			if (esLlaves())
				continue;
			if (esParentesis())
				continue;
			if (esCorchetes())
				continue;
			if (esOperadorLogico())
				continue;
			if (esNatural())
				continue;
			if (esOperadorRelacional())
				continue;
			if (esOperadorAritmetico())
				continue;
			if (esOperadorAsignacion())
				continue;
			if (esIncrementoDecremento())
				continue;
			if (esPalabraReservada())
				continue;
			if (esIdentificador())
				continue;
			if (esCadenaCaracteres())
			 	continue;
			if (esCaracter())
				continue;

			// Si el caracter actual no pertenece a ninguna categoria reconocida por el
			// lenguaje, lo guarda como algo desconocido
			listaTokens.add(new Token(Categoria.DESCONOCIDO, "" + caracterActual, filaActual, colActual));

			obtenerSgteCaracter();
		}
		// Collections.sort((List<Token>) listaTokens);

	}

	/**
	 * Metodo que me permite idenificar una secuencia de caracteres como token tipo
	 * Categoria.HEXADECIMAL
	 * 
	 * @return true si pertence a la Categoria.HEXADECIMAL
	 */
	public boolean esHexadecimal() {

		// RI
		if (caracterActual != 'h') {
			return false;
		}

		// Variables Temporales
		String palabra = "";
		int posTemp = posActual;
		int fila = filaActual;
		int columna = colActual;

		// Transicion 1
		palabra = hacerTransicion(palabra, caracterActual);

		// BT
		if (caracterActual != 'x') {
			hacerBT(posTemp, fila, columna);
			return false;
		}

		// Transicion 2
		palabra = hacerTransicion(palabra, caracterActual);

		// BT
		if (!(Character.isDigit(caracterActual) || isLetraHexa(caracterActual))) {
			hacerBT(posTemp, fila, columna);
			return false;
		}

		// Bucle
		palabra = hacerTransicion(palabra, caracterActual);

		while (Character.isDigit(caracterActual) || isLetraHexa(caracterActual)) {
			palabra = hacerTransicion(palabra, caracterActual);
		}

		// AA
		listaTokens.add(new Token(Categoria.HEXADECIMAL, palabra, fila, columna));
		return true;
	}
	
	/**
	 * Metodo que me permite idenificar una secuencia de caracteres como token tipo
	 * Categoria.CARACTER
	 * 
	 * @return true si pertence a la Categoria.CARACTER
	 */
	public boolean esCaracter() {
		
		// RI
		if (caracterActual != '\'') {
			return false;
		}

		String palabra = "";
		int posTemp = posActual;
		int fila = filaActual;
		int columna = colActual;
		//Flag para verificar si se encontro un error en la cadena por una barra invertida
		boolean flag = false;
		
		//flag que me permite identificar si se dejan los apostrofes vacios
		boolean flag2 = false;
		
		//flag que me permite identificar se hay un salto de linea despues del apostrofe 
		boolean flag3 = false;

		// Transicion 1
		palabra = hacerTransicion(palabra, caracterActual);
		
		if(caracterActual == '\n') 
		{
			flag3 = true;
		}
		
		if(!flag3) 
		{
			if(caracterActual == '\\') 
			{
				palabra = hacerTransicion(palabra, caracterActual);
				
				if(caracterActual != 'n' && caracterActual != '\'' && caracterActual != 't' && caracterActual != 'b' && caracterActual != 'f' && caracterActual != 'r' && caracterActual != '\\') 
				{
					flag = true;
				}
				palabra = hacerTransicion(palabra, caracterActual);
			}
			else 
			{
				if(caracterActual == '\'') 
				{
					flag2 = true;
				}
				else 
				{
					palabra = hacerTransicion(palabra, caracterActual);
				}
			}			
		}
		if(caracterActual == '\'' && !flag && !flag2 && !flag3) 
		{
			palabra = hacerTransicion(palabra, caracterActual);
			listaTokens.add(new Token(Categoria.CARACTER, palabra, fila, columna));
		}
		else 
		{
			if(flag2) 
			{
				// ERROR
				palabra = hacerTransicion(palabra, caracterActual);
				listaTokens.add(new Token(Categoria.ERROR_LEXICO, palabra, fila, columna));
				listaErrores.add(new ErrorLexico("No se puede dejar el caracter vacio", fila));
			}
			else if(caracterActual != '\'' || flag3) 
			{
				// ERROR
				palabra = hacerTransicion(palabra, caracterActual);
				listaTokens.add(new Token(Categoria.ERROR_LEXICO, palabra, fila, columna));
				listaErrores.add(new ErrorLexico("El apostrofe nunca se cerro", fila));
				
				if(flag)
				{
					// ERROR
					listaErrores.add(new ErrorLexico("Secuencia de escape invalida (validas son \\\\, \\n, \\t, \\f, \\\", \\b, \\r)", fila));
				}
			}
			else if(caracterActual == '\'' && flag)
			{
				// ERROR
				palabra = hacerTransicion(palabra, caracterActual);
				listaTokens.add(new Token(Categoria.ERROR_LEXICO, palabra, fila, columna));
				listaErrores.add(new ErrorLexico("Secuencia de escape invalida (validas son \\\\, \\n, \\t, \\f, \\\", \\b, \\r)", fila));
			}
			
			
			
		}		
		return true;
	}

	/**
	 * Metodo que me permite identificar una secuencia de caracteres como token tipo
	 * Categoria.PALABRA_RESERVADA
	 * 
	 * @return true si la secuencia de caracteres corresponden a
	 *         Categoria.PALABRA_RESERVADA
	 */
	public boolean esPalabraReservada() {

		//RI
		if (!Character.isLetter(caracterActual)) {
			return false;
		}
		
		//Variables temporales
		String palabra = "";
		int posTemp = posActual;
		int fila = filaActual;
		int columna = colActual;

		// Transicion 1
		palabra = hacerTransicion(palabra, caracterActual);

		boolean flag = false;
		//Bucle
		while (Character.isLetter(caracterActual) && flag == false) {
			//Transaccion 2
			palabra = hacerTransicion(palabra, caracterActual);
			
			if (contenidoArregloReservadas(palabra)) {
				flag = true;
			}
		}

		//BT
		if (!contenidoArregloReservadas(palabra)) {
			hacerBT(posTemp, fila, columna);
			return false;
		} 

		listaTokens.add(new Token(Categoria.PALABRA_RESERVADA, palabra, fila, columna));
		return true;
		
	}

	/**
	 * Metodo que me permite identificar una secuencia de caracteres como token
	 * Categoria.COMENTARIO_LINEA
	 * 
	 * @return true si la secuencia de caracteres corresponden a
	 *         Categoria.COMENTARIO_LINEA
	 */
	public boolean esComentarioLinea() {

		// RI
		if (caracterActual != '#') {
			return false;
		}

		// Variables Temporales
		String palabra = "";
		int fila = filaActual;
		int columna = colActual;
		int posTemp = posActual;

		// Bucle
		while (caracterActual != '\n' && caracterActual != finCodigo) {
			palabra = hacerTransicion(palabra, caracterActual);
		}
		listaTokens.add(new Token(Categoria.COMENTARIO_LINEA, palabra, fila, columna));
		return true;

	}

	/**
	 * Metodo que me permite identificar una secuencia de caracteres como token
	 * Categoria.COMENTARIO_BLOQUE
	 * 
	 * @return true si la secuencia de caracteres corresponden a
	 *         Categoria.COMENTARIO_BLOQUE
	 */
	public boolean esComentarioBloque() {

		// Rechazo Inmediato
		if (caracterActual != '$') {
			return false;
		}

		// Variables Temporales
		String palabra = "";
		int fila = filaActual;
		int columna = colActual;
		int posTemp = posActual;

		// Transicion 1
		palabra = hacerTransicion(palabra, caracterActual);

		// BackTracking
		if (caracterActual != '$') {
			hacerBT(posTemp, fila, columna);
			return false;
		}

		// Bucle
		boolean flag = true; // Mientras no finalize con doble signo peso($$)
		while (flag && caracterActual != finCodigo) {
			palabra = hacerTransicion(palabra, caracterActual);

			if (caracterActual == '$') {
				palabra = hacerTransicion(palabra, caracterActual);
				if (caracterActual != finCodigo) {
					if (caracterActual == '$') {
						palabra = hacerTransicion(palabra, caracterActual);
						flag = false;
					}
				}
			}
		}

		// Reportar Error
		if (caracterActual == finCodigo && flag) {
			listaTokens.add(new Token(Categoria.ERROR_LEXICO, palabra, fila, columna));
			listaErrores.add(new ErrorLexico("El comentario nunca se cerro", fila));
			return false;
		}

		listaTokens.add(new Token(Categoria.COMENTARIO_BLOQUE, palabra, fila, columna));
		return true;

	}

	/**
	 * Metodo que me permite identificar una secuencia de caracteres como token tipo
	 * Categoria.CADENA_CARACTERES
	 * 
	 * @return true si la secuencia de caracteres corresponden a
	 *         Categoria.CADENA_CARACTERES
	 * @throws ErrorLexico 
	 */
	public boolean esCadenaCaracteres() throws ErrorLexico {

		// 32 = caracter de "
		// RI
		if (caracterActual != 34) {
			return false;
		}

		String palabra = "";
		int posTemp = posActual;
		int fila = filaActual;
		int columna = colActual;
		//Flag para verificar si se encontro un error en la cadena por una barra invertida
		boolean flag = false;

		// Transicion 1
		palabra = hacerTransicion(palabra, caracterActual);

		// Bucle
		while (caracterActual != 34 && caracterActual != finCodigo && caracterActual != '\n') {

			// 92 = caracter de \
			if (caracterActual == 92) {
				
				palabra = hacerTransicion(palabra, caracterActual);

				// RE
				if (caracterActual != '\\' && caracterActual != 'n' && caracterActual != 't' && caracterActual != '"'
						&& caracterActual != 'f' && caracterActual != 'b' && caracterActual != 'r'
						&& caracterActual != '\'') {
					// ERROR
					flag = true;
				}else {
					palabra = hacerTransicion(palabra, caracterActual);
				}			
				
			} else {
				palabra = hacerTransicion(palabra, caracterActual);
			}
			
		}
		
		if(flag) {
			
			palabra = hacerTransicion(palabra, caracterActual);
			listaTokens.add(new Token(Categoria.ERROR_LEXICO, palabra, fila, columna));
			listaErrores.add(new ErrorLexico("Secuencia de escape invalida (validas son \\\\, \\n, \\t, \\f, \\\", \\b, \\r)", fila));
			return true;
			
		}else if (caracterActual != finCodigo && caracterActual != '\n') {

			palabra = hacerTransicion(palabra, caracterActual);
			listaTokens.add(new Token(Categoria.CADENA_CARACTERES, palabra, fila, columna));
			return true;

		} else {

			// ERROR
			palabra = hacerTransicion(palabra, caracterActual);
			listaTokens.add(new Token(Categoria.ERROR_LEXICO, palabra, fila, columna));
			listaErrores.add(new ErrorLexico("Las comillas iniciales nunca se cerraron", fila));
			return true;
			
		}
	}

	/**
	 * Metodo que me permite identificar un caracter como token tipo
	 * Categoria.LLAVES_ABRE o Categoria.LLAVES_CIERRA
	 * 
	 * @return true si un caracter corresponden a Categoria.LLAVES_ABRE o
	 *         Categoria.LLAVES_CIERRA
	 */
	public boolean esLlaves() {

		// Rechazo Inmediato
		if (caracterActual != '{' && caracterActual != '}') 
		{
			return false;
		}
		
		String palabra = "";
		int fila = filaActual;
		int columna = colActual;

		// Transicion
		palabra = hacerTransicion(palabra, caracterActual);

		if (palabra.equals("{")) {
			listaTokens.add(new Token(Categoria.LLAVES_ABRE, palabra, fila, columna));
		} else {
			listaTokens.add(new Token(Categoria.LLAVES_CIERRA, palabra, fila, columna));
		}

		return true;
	}

	/**
	 * Metodo que me permite identificar un caracter como token tipo
	 * Categoria.PARENTESIS_ABRE o Categoria.PARENTESIS_CIERRA
	 * 
	 * @return true si un caracter corresponden a Categoria.PARENTESIS_ABRE o
	 *         Categoria.PARENTESIS_CIERRA
	 */
	public boolean esParentesis() {

		// Rechazo Inmediato
		if (caracterActual != '(' && caracterActual != ')') 
		{
			return false;
		}
		
		String palabra = "";
		int fila = filaActual;
		int columna = colActual;

		// Transicion
		palabra = hacerTransicion(palabra, caracterActual);

		if (palabra.equals("(")) {
			listaTokens.add(new Token(Categoria.PARENTESIS_ABRE, palabra, fila, columna));
		} else {
			listaTokens.add(new Token(Categoria.PARENTESIS_CIERRA, palabra, fila, columna));
		}

		return true;
	}
	
	/**
	 * Metodo que me permite identificar un caracter como token tipo
	 * Categoria.CORCHETES_ABRE o Categoria.CORCHETES_CIERRA
	 * 
	 * @return true si un caracter corresponden a Categoria.CORCHETES_ABRE o
	 *         Categoria.CORCHETES_CIERRA
	 */
	public boolean esCorchetes() {

		// Rechazo Inmediato
		if (caracterActual != '[' && caracterActual != ']') 
		{
			return false;
		}
		
		String palabra = "";
		int fila = filaActual;
		int columna = colActual;

		// Transicion
		palabra = hacerTransicion(palabra, caracterActual);

		if (palabra.equals("[")) {
			listaTokens.add(new Token(Categoria.CORCHETES_ABRE, palabra, fila, columna));
		} else {
			listaTokens.add(new Token(Categoria.CORCHETES_CIERRA, palabra, fila, columna));
		}

		return true;
	}

	/**
	 * Metodo que me permite identificar un caracter como token tipo
	 * Categoria.TERMINAL
	 * 
	 * @return true si un caracter corresponden a Categoria.TERMINAL
	 */
	public boolean esTerminal() {

		// RI
		if (caracterActual != '@') {
			return false;
		}
		
		// Variable temporales
		String palabra = "";
		int fila = filaActual;
		int columna = colActual;

		// Transicion
		palabra = hacerTransicion(palabra, caracterActual);

		listaTokens.add(new Token(Categoria.TERMINAL, palabra, fila, columna));
		return true;
	}

	/**
	 * Metodo que me permite identificar un caracter como token tipo
	 * Categoria.PUNTO
	 * 
	 * @return true si un caracter corresponden a Categoria.PUNTO
	 */
	public boolean esPunto() {

		// RI
		if (caracterActual != '.') {
			return false;
		}
		// Variables temporales
		String palabra = "";
		int fila = filaActual;
		int columna = colActual;
		int posTemp = posActual;

		// Transicion
		palabra = hacerTransicion(palabra, caracterActual);
		
		// BackTracking
		if(Character.isDigit(caracterActual)) 
		{
			hacerBT(posTemp, fila, columna);
			return false;
		}

		listaTokens.add(new Token(Categoria.PUNTO, palabra, fila, columna));
		return true;
	}
	
	/**
	 * Metodo que me permite identificar un caracter como token tipo
	 * Categoria.DOS_PUNTOS
	 * 
	 * @return true si un caracter corresponden a Categoria.DOS_PUNTOS
	 */
	public boolean esDosPuntos() {

		// RI
		if (caracterActual != ':') {
			return false;
		}
		// Variables temporales
		String palabra = "";
		int fila = filaActual;
		int columna = colActual;

		// Transicion
		palabra = hacerTransicion(palabra, caracterActual);

		listaTokens.add(new Token(Categoria.DOS_PUNTOS, palabra, fila, columna));
		return true;
	}
	
	/**
	 * Metodo que me permite identificar un caracter como token tipo
	 * Categoria.SEPARADOR
	 * 
	 * @return true si un caracter corresponden a Categoria.SEPARADOR
	 */
	public boolean esSeparador() {

		// RI
		if (caracterActual != ',') {
			return false;
		}
		// Variables temporales
		String palabra = "";
		int fila = filaActual;
		int columna = colActual;

		// Transicion
		palabra = hacerTransicion(palabra, caracterActual);

		listaTokens.add(new Token(Categoria.SEPARADOR, palabra, fila, columna));
		return true;
	}

	/**
	 * Metodo que me permite identificar un caracter o caracteres como token tipo
	 * Categoria.OPERADOR_RELACIONAL
	 * 
	 * @return true si un caracter o caracteres corresponden a
	 *         Categoria.OPERADOR_RELACIONAL
	 */
	public boolean esOperadorRelacional() {

		//RI
		if (caracterActual != '<' && caracterActual != '>' && caracterActual != '=' && caracterActual != '!') {
			return false;
		}
		
		//Variables temporales
		String palabra = "";
		int posTemp = posActual;
		int fila = filaActual;
		int columna = colActual;

		// Transición 1
		palabra = hacerTransicion(palabra, caracterActual);

		if(palabra.equals("=") || palabra.equals("!")) {
			if(caracterActual != '=') {
				hacerBT(posTemp, fila, columna);
				return false;
			}
			
			palabra = hacerTransicion(palabra, caracterActual);
		}else if(caracterActual == '=') {
			palabra = hacerTransicion(palabra, caracterActual);
		}

		listaTokens.add(new Token(Categoria.OPERADOR_RELACIONAL, palabra, fila, columna));
		return true;

		
	}

	/**
	 * Metodo que me permite identificar un caracter o caracteres como token tipo
	 * Categoria.OPERADOR_ASIGNACION
	 * 
	 * @return true si un caracter o caracteres corresponden a
	 *         Categoria.OPERADOR_ASIGNACION
	 */
	public boolean esOperadorAsignacion() {
		
		// Rechazo Inmediato
		if (caracterActual != '+' && caracterActual != '-' && caracterActual !='*' && caracterActual != '/' && caracterActual != '%' && caracterActual != '=' && caracterActual != '^') {
			return false;
		}

		// Variables Temporales
		String palabra = "";
		int fila = filaActual;
		int columna = colActual;
		int posTemp = posActual;
		
		if(caracterActual == '=') 
		{
			// Transicion 1
			palabra = hacerTransicion(palabra, caracterActual);
			
			// BackTracking 
			if(caracterActual == '=') 
			{
				hacerBT(posTemp, fila, columna);
				return false;
			}
		}
		else 
		{
			// Transicion 1
			palabra = hacerTransicion(palabra, caracterActual);
			
			// BackTracking 
			if(caracterActual != '=') 
			{
				hacerBT(posTemp, fila, columna);
				return false;
			}
			else 
			{
				// Transicion 1
				palabra = hacerTransicion(palabra, caracterActual);
			}
		}

		listaTokens.add(new Token(Categoria.OPERADOR_ASIGNACION, palabra, fila, columna));
		return true;

	}
	
	/**
	 * Metodo que me permite identificar un caracter o caracteres como token tipo
	 * Categoria.INCREMENTO_DECREMENTO
	 * 
	 * @return true si un caracter o caracteres corresponden a
	 *         Categoria.INCREMENTO_DECREMENTO
	 */
	public boolean esIncrementoDecremento() {
		
		// Rechazo Inmediato
		if (caracterActual != '+' && caracterActual != '-') {
			return false;
		}

		// Variables Temporales
		String palabra = "";
		int fila = filaActual;
		int columna = colActual;
		int posTemp = posActual;
		
		if(caracterActual == '+') 
		{
			// Transicion 1
			palabra = hacerTransicion(palabra, caracterActual);
			
			// BackTracking 
			if(caracterActual != '+') 
			{
				hacerBT(posTemp, fila, columna);
				return false;
			}
			
			// Transicion 2
			palabra = hacerTransicion(palabra, caracterActual);
			
		}
		else 
		{
			// Transicion 1
			palabra = hacerTransicion(palabra, caracterActual);
			
			// BackTracking 
			if(caracterActual != '-') 
			{
				hacerBT(posTemp, fila, columna);
				return false;
			}
			
			// Transicion 2
			palabra = hacerTransicion(palabra, caracterActual);
		}

		listaTokens.add(new Token(Categoria.INCREMENTO_DECREMENTO, palabra, fila, columna));
		return true;

	}

	/**
	 * Metodo que me permite identificar un caracter como token tipo
	 * Categoria.OPERADOR_ARITMETICO
	 * 
	 * @return true si un caracter corresponden a Categoria.OPERADOR_ARITMETICO
	 */
	public boolean esOperadorAritmetico() {
		// Rechazo inmendiato
		if (caracterActual != '+' && caracterActual != '-' && caracterActual != '*' && caracterActual != '/'
				&& caracterActual != '%' && caracterActual != '^') {
			return false;
		}

		// Variables temporales
		String palabra = "";
		int fila = filaActual;
		int columna = colActual;
		int posTemp = posActual;

		if (caracterActual == '+') {
			palabra = hacerTransicion(palabra, caracterActual);
			// BackTracking
			if (caracterActual == '+' || caracterActual == '=') {
				hacerBT(posTemp, fila, columna);
				return false;
			}
		} else if (caracterActual == '-') {
			palabra = hacerTransicion(palabra, caracterActual);
			// BackTracking
			if (caracterActual == '-' || caracterActual == '=') {
				hacerBT(posTemp, fila, columna);
				return false;
			}
		} else if (caracterActual == '*') {
			palabra = hacerTransicion(palabra, caracterActual);
			// BackTracking
			if (caracterActual == '=') {
				hacerBT(posTemp, fila, columna);
				return false;
			}
		} else if (caracterActual == '/') {
			palabra = hacerTransicion(palabra, caracterActual);
			// BackTracking
			if (caracterActual == '=') {
				hacerBT(posTemp, fila, columna);
				return false;
			}
		} else if (caracterActual == '%') {
			palabra = hacerTransicion(palabra, caracterActual);
			// BackTracking
			if (caracterActual == '%' || caracterActual == '=') {
				hacerBT(posTemp, fila, columna);
				return false;
			}
		} else if (caracterActual == '^') {
			palabra = hacerTransicion(palabra, caracterActual);
			// BackTracking
			if (caracterActual == '=') {
				hacerBT(posTemp, fila, columna);
				return false;
			}
		}

		listaTokens.add(new Token(Categoria.OPERADOR_ARITMETICO, palabra, fila, columna));
		return true;
	}

	/**
	 * Metodo que me permite identificar una secuencia de caracteres como token
	 * Categoria.REAL
	 * 
	 * @return true si la secuencia de caracteres corresponden a Categoria.REAL
	 */
	public boolean esReal() {

		//RI
		if (!Character.isDigit(caracterActual) && caracterActual != '.') {
			return false;		
		}
		
		//Variables temporales
		String palabra = "";
		int posTemp = posActual;
		int fila = filaActual;
		int columna = colActual;

		// Transición 1
		palabra = hacerTransicion(palabra, caracterActual);

		if (palabra.equals(".")) {
			
			if(!Character.isDigit(caracterActual))
			{
				hacerBT(posTemp, fila, columna);
				return false;
			}
			
			while (Character.isDigit(caracterActual)) {
				//Transicion 2
				palabra = hacerTransicion(palabra, caracterActual);
			}
			
		} else if (Character.isDigit(palabra.charAt(0))) {
			// Este flag verifica que ya se haya pasado una vez por un punto ('.'), ya que
			// obivamente un real solo tiene un punto
			boolean flag = false;

			while (Character.isDigit(caracterActual) || (caracterActual == '.' && flag == false)) {
				if (caracterActual == '.') {
					flag = true;
				}

				//Transicion 3
				palabra = hacerTransicion(palabra, caracterActual);
			}
			
			if(flag == false) {
				hacerBT(posTemp, fila, columna);
				return false;
			}
		}

		listaTokens.add(new Token(Categoria.REAL, palabra, fila, columna));
		return true;
		
	}

	/**
	 * Metodo que me permite identificar una secuencia de caracteres como token
	 * Categoria.OPERADOR_LOGICO
	 * 
	 * @return true si la secuencia de caracteres corresponden a
	 *         Categoria.OPERADOR_LOGICO
	 */
	public boolean esOperadorLogico() {
		
		//RI
		if(caracterActual != 'y' && caracterActual != 'o' && caracterActual != '!') {
			return false;
		}
		
		//Variables temporales
		String palabra = "";
		int posTemp = posActual;
		int fila = filaActual;
		int columna = colActual;
		
		//Transicion 1
		palabra = hacerTransicion(palabra, caracterActual);
		
		//BT
		if (palabra.equals("!") && caracterActual == '=') {
			hacerBT(posTemp, fila, columna);	
			return false;
		}
		
		if(palabra.equals(caracterActual+"")) {
			//Transicion 2
			palabra = hacerTransicion(palabra, caracterActual);
		}else {
			hacerBT(posTemp, fila, columna);	
			return false;
		}
		
		listaTokens.add(new Token(Categoria.OPERADOR_LOGICO, palabra, fila, columna));
		return true;
			
	}

	/**
	 * Metodo que me permite identificar una secuencia de caracteres como token
	 * Categoria.ENTERO
	 * 
	 * @return true si la secuencia de caracteres corresponden a Categoria.ENTERO
	 */
	public boolean esNatural() {

		// RI
		if (!Character.isDigit(caracterActual)) {
			return false;
		}
		
		//Variables temporales
		String palabra = "";
		int posTemp = posActual;
		int fila = filaActual;
		int columna = colActual;

		// Transición
		palabra = hacerTransicion(palabra, caracterActual);
		
		//BT 1
		if(caracterActual == '.') {
			hacerBT(posTemp, fila, columna);
			return false;
		}

		//Bucle
		while (Character.isDigit(caracterActual)) {
			//Transicion 2
			palabra = hacerTransicion(palabra, caracterActual);
		}
		
		//BT 2
		if(caracterActual == '.') {
			hacerBT(posTemp, fila, columna);
			return false;
		}

		listaTokens.add(new Token(Categoria.ENTERO, palabra, fila, columna));
		return true;
		
	}

	/**
	 * Metodo que me permite identificar si un caracter es de tipo hexaDecimal (A,
	 * B, C, D, E, F)
	 * 
	 * @param caracter caracter a indentificar como hexadecimal
	 * @return truen si es un caracter de tipo hexadecimal
	 */
	public boolean isLetraHexa(char caracter) {
		return caracter >= 65 && caracter <= 70 ? true : false;
	}

	/**
	 * Metodo que me permite identificar una secuencia de caracteres como token
	 * Categoria.IDENTIFICADOR
	 * 
	 * @return true si la secuencia de caracteres corresponden a
	 *         Categoria.IDENTIFICADOR
	 */
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

	/**
	 * Metodo que me permite obtener el siguiente caracter en secuencia o que le
	 * procede al actual omitiendo saltos de linea y tabulaciones
	 */
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

	/**
	 * Metodo que me permite obtener el siguiente caracter en secuencia o que le
	 * precede al actual
	 */
	public void obtenerAntCaracter() {

		posActual--;

		if (posActual > 0) {

			colActual--;

			caracterActual = codigoFuente.charAt(posActual);
		} else {
			caracterActual = codigoFuente.charAt(0);
		}

	}

	/**
	 * Permite obtener el la lista de tokens
	 * 
	 * @return lista de tokens
	 */
	public ArrayList<Token> getListaTokens() {
		return listaTokens;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<ErrorLexico> getListaErrores() {
		return listaErrores;
	}

	/**
	 * Metodo que me permite Hacer BackTracking
	 * 
	 * @param posInicial
	 * @param fila
	 * @param columna
	 */
	public void hacerBT(int posTemp, int fila, int columna) {
		posActual = posTemp;
		filaActual = fila;
		colActual = columna;
		caracterActual = codigoFuente.charAt(posTemp);
	}

	/**
	 * Me permite hacer una transicion, a�adiendo el caracter actual
		caracterActual = codigoFuente.charAt(posInicial);
	 * 
	 * @param palabra
	 * @param letra
	 * @return
	 */
	public String hacerTransicion(String palabra, char letra) {
		obtenerSgteCaracter();
		palabra += letra;
		return palabra;
	}

	/**
	 * Metodo que segun una palabra, indica si pertenece a las palbras reservadas
	 * del lenguaje
	 * 
	 * @param palabra, palabra para indentificar como palabra reservada
	 * @return true si pertenece al arreglo de palabras reservadas
	 */
	public boolean contenidoArregloReservadas(String palabra) {
		for (int i = 0; i < palabrasReservadas.length; i++) {
			if (palabrasReservadas[i].equals(palabra)) {
				return true;
			}
		}
		return false;
	}
}
