package co.sis.crirowil.persistencia;

import java.util.ArrayList;

/**
 * Clase que analiza los tokens del lenguaje de programacion dado un codigo fuente
 * @author Wilmar Stiven Valencia Cardona
 * @author Juan Manuel Roa Mejia
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
	private String[] palabrasReservadas = { "ciclo", "metodo", "cadena", "entero", "real", "devolver", "importar", "sisas", "nonas", "nada" };
	
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
	 * Constructor
	 * @param codigoFuente
	 */
	public AnalizadorLexico(String codigoFuente) {
		this.codigoFuente = codigoFuente;
		this.listaTokens = new ArrayList<Token>();
		this.caracterActual = codigoFuente.charAt(posActual);
		this.finCodigo = 0;
	}

	/**
	 * Metodo que analiza el codigo fuente y define el tipo de los token segun con las categorias que cuenta el lenguaje 
	 */
	public void analizar() {

		while (caracterActual != finCodigo) {

			// Desprecia saltos de linea y tabulaciones
			if (caracterActual == ' ' || caracterActual == '\n' || caracterActual == '\t' || caracterActual == '\r') {
				obtenerSgteCaracter();
				continue;
			}

			if(esTerminal()) 
				continue;
			if(esSeparador())
				continue;
			if (esLlaves())
				continue;
			if (esParentesis())
				continue;
			if (esHexadecimal())
				continue;
			if (esOperadorLogico())
				continue;
			if (esReal())
				continue;
			if (esNatural())
				continue;
			if (esOperadorRelacional())
				continue;
			if (esOperadorAsignacion())
				continue;
			if (esOperadorAritmetico())
				continue;
			if (esPalabraReservada())
				continue;
			if(esComentario())
				continue;
			if (esIdentificador())
				continue;
			if(esCadenaCaracteres())
				continue;

			//Si el caracter actual no pertenece a ninguna categoria reconocida por el lenguaje, lo guarda como algo desconocido
			listaTokens.add(new Token(Categoria.DESCONOCIDO, "" + caracterActual, filaActual, colActual));

			obtenerSgteCaracter();
		}

		//Collections.sort((List<Token>) listaTokens);

	}

	/**
	 * Metodo que me permite idenificar una secuencia de caracteres como token tipo  Categoria.HEXADECIMAL
	 * @return true si pertence a la Categoria.HEXADECIMAL
	 */
	public boolean esHexadecimal() {
		String palabra = "";
		int posTemp = posActual;
		int fila = filaActual;
		int columna = colActual;
		if (caracterActual == 'h') {
			palabra += caracterActual;
			obtenerSgteCaracter();

			if (caracterActual == 'x') {
				palabra += caracterActual;
				obtenerSgteCaracter();
				if (Character.isDigit(caracterActual) || isLetraHexa(caracterActual)) {
					palabra += caracterActual;
					obtenerSgteCaracter();
					while (Character.isDigit(caracterActual) || isLetraHexa(caracterActual)) {
						palabra += caracterActual;
						obtenerSgteCaracter();
					}
					listaTokens.add(new Token(Categoria.HEXADECIMAL, palabra, fila, columna));
					return true;
				}
			}
		}
		posActual = posTemp;
		filaActual = fila;
		colActual = columna;
		caracterActual = codigoFuente.charAt(posActual);
		return false;
	}

	/**
	 * Metodo que me permite identificar si un caracter es de tipo hexaDecimal (A, B, C, D, E, F)
	 * @param caracter caracter a indentificar como hexadecimal
	 * @return truen si es un caracter de tipo hexadecimal
	 */
	public boolean isLetraHexa(char caracter) {
		return caracter >= 65 && caracter <= 70 ? true : false;
	}

	/**
	 * Metodo que segun una palabra, indica si pertenece a las palbras reservadas del lenguaje
	 * @param palabra,  palabra para indentificar como palabra reservada
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

	/**
	 * Metodo que me permite identificar una secuencia de caracteres como token tipo Categoria.PALABRA_RESERVADA 
	 * @return true si la secuencia de caracteres corresponden a Categoria.PALABRA_RESERVADA
	 */
	public boolean esPalabraReservada() {

		if (Character.isLetter(caracterActual)) {
			String palabra = "";
			int posTemp = posActual;
			int fila = filaActual;
			int columna = colActual;

			// Transicion
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
				posActual = posTemp; 
				caracterActual = codigoFuente.charAt(posActual);
				filaActual = fila;
				colActual = columna;

				return false;
			}

			return true;
		}

		// RI
		return false;
	}

	
	/**
	 * Metodo que me permite identificar una secuencia de caracteres como token Categoria.COMENTARIO 
	 * @return true si la secuencia de caracteres corresponden a Categoria.COMENTARIO
	 */
	public boolean esComentario()
	{
		if(caracterActual == '$')
		{
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;
			int posTemp = posActual;
			
			palabra+=caracterActual;
			obtenerSgteCaracter();
			
			
			if(caracterActual == '$')
			{
				palabra+=caracterActual;
				obtenerSgteCaracter();
			
				
				while(caracterActual != '$' && caracterActual != finCodigo)
				{
					palabra += caracterActual;
					obtenerSgteCaracter();
				}
				
				if(caracterActual == '$')
				{
					palabra+=caracterActual;
					obtenerSgteCaracter();
					if(caracterActual == '$')
					{
						palabra+=caracterActual;
						obtenerSgteCaracter();
						listaTokens.add(new Token(Categoria.COMENTARIO,palabra,fila,columna));
						return true;
					}
					else
					{
						
						this.caracterActual = codigoFuente.charAt(posTemp);	// Se regresa al caracter que lo inicio todo
						this.posActual = posTemp;							//Se regresa a la pos del caracter	
						this.filaActual = fila;								//Se regresa a la fila del carater
						this.colActual = columna;							//Se regresa a la columna del caracter
						return false;
					}
				}
				else
				{
					
					this.caracterActual = codigoFuente.charAt(posTemp);		//Lo de arriba
					this.posActual = posTemp;
					this.filaActual = fila;
					this.colActual = columna;
					return false;
				}
			}
			else
			{
				obtenerAntCaracter();
				return false;
						
			}
			
		}
		
	return false;
		
		
	}
	
	/**
	 * Metodo que me permite identificar una secuencia de caracteres como token tipo Categoria.CADENA_CARACTERES 
	 * @return true si la secuencia de caracteres corresponden a Categoria.CADENA_CARACTERES
	 */
	public boolean esCadenaCaracteres() {
		
		if(caracterActual == 34)
		{
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;
			
			palabra+= caracterActual;
			obtenerSgteCaracter();
			
			int aDevolverse = 1; // Este numero solo se aplicara en caso de que haya una comilla doble y nunca se cierre. 
			
			while(caracterActual != 34 && caracterActual != finCodigo)
			{
				aDevolverse++;	
				palabra+=caracterActual;
				obtenerSgteCaracter();
					
			}
			
			if(caracterActual != finCodigo)
			{
				
				if(caracterActual == 34)
				{
					palabra+=caracterActual;
					obtenerSgteCaracter();
					listaTokens.add(new Token(Categoria.CADENA_CARACTERES,palabra,fila,columna));
					return true;
				}
			}
			else
			{
				for(int i = 0 ; i < aDevolverse ; i++) //Se devuelve solo hasta el caracter siguiente de la comilla doble abierta peronunca cerrada
				{
					obtenerAntCaracter();
				}
				return false;
				
			}
			
			
		}
		return false;
	}
	
	/**
	 * Metodo que me permite identificar un caracter como token tipo Categoria.LLAVES_ABRE o Categoria.LLAVES_CIERRA
	 * @return true si un caracter corresponden a Categoria.LLAVES_ABRE o Categoria.LLAVES_CIERRA
	 */
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

	/**
	 * Metodo que me permite identificar un caracter como token tipo Categoria.PARENTESIS_ABRE o Categoria.PARENTESIS_CIERRA
	 * @return true si un caracter corresponden a Categoria.PARENTESIS_ABRE o Categoria.PARENTESIS_CIERRA
	 */
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

	/**
	 * Metodo que me permite identificar un caracter como token tipo Categoria.TERMINAL
	 * @return true si un caracter corresponden a Categoria.TERMINAL
	 */
	public boolean esTerminal() {

		if (caracterActual == '@') {
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;

			// Transición
			palabra += caracterActual;
			obtenerSgteCaracter();

			listaTokens.add(new Token(Categoria.TERMINAL, palabra, fila, columna));
			return true;

		}

		// RI
		return false;
	}

	/**
	 * Metodo que me permite identificar un caracter como token tipo Categoria.SEPARADOR
	 * @return true si un caracter corresponden a Categoria.SEPARADOR
	 */
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
	
	/**
	 * Metodo que me permite identificar un caracter o caracteres como token tipo Categoria.OPERADOR_RELACIONAL
	 * @return true si un caracter o caracteres corresponden a Categoria.OPERADOR_RELACIONAL
	 */
	public boolean esOperadorRelacional() {

		if (caracterActual == '<' || caracterActual == '>' || caracterActual == '=' || caracterActual == '!') {
			String palabra = "";
			int posTemp = posActual;
			int fila = filaActual;
			int columna = colActual;


			// Transición
			palabra += caracterActual;
			obtenerSgteCaracter();

			if(caracterActual == '=') {
				palabra += caracterActual;
				obtenerSgteCaracter();	
			}else if ((palabra.equals("=") || palabra.equals("!"))  && caracterActual != '=') {
				posActual = posTemp; 
				caracterActual = codigoFuente.charAt(posActual);
				filaActual = fila;
				colActual = columna;

				return false;
			}

			listaTokens.add(new Token(Categoria.OPERADOR_RELACIONAL, palabra, fila, columna));
			return true;

		}

		// RI
		return false;
	}

	/**
	 * Metodo que me permite identificar un caracter o caracteres como token tipo Categoria.OPERADOR_ASIGNACION
	 * @return true si un caracter o caracteres corresponden a Categoria.OPERADOR_ASIGNACION
	 */
	public boolean esOperadorAsignacion() {

		if (caracterActual == '+' || caracterActual == '=' || caracterActual == '*' || caracterActual == '/'
				|| caracterActual == '%' || caracterActual == '^' || caracterActual == '-') {
			String palabra = "";
			int posTemp = posActual;
			int fila = filaActual;
			int columna = colActual;


			// Transición
			palabra += caracterActual;
			obtenerSgteCaracter();

			if(palabra.equals("=")) {
				listaTokens.add(new Token(Categoria.OPERADOR_ASIGNACION, palabra, fila, columna));
				return true;
			}else if (palabra.equals(caracterActual + "") && (palabra.equals("+") || palabra.equals("-"))) {
				palabra += caracterActual;
				obtenerSgteCaracter();
			} else if (!palabra.equals("=") && caracterActual == '=') {			
				palabra += caracterActual;
				obtenerSgteCaracter();		
			}else {			
				posActual = posTemp; 
				caracterActual = codigoFuente.charAt(posActual);
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

	/**
	 * Metodo que me permite identificar un caracter como token tipo Categoria.OPERADOR_ARITMETICO
	 * @return true si un caracter corresponden a Categoria.OPERADOR_ARITMETICO
	 */
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

	/**
	 * Metodo que me permite identificar una secuencia de caracteres como token Categoria.REAL 
	 * @return true si la secuencia de caracteres corresponden a Categoria.REAL
	 */
	public boolean esReal() {

		if (Character.isDigit(caracterActual) || caracterActual == '.') {
			String palabra = "";
			int posTemp = posActual;
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
				posActual = posTemp; 
				caracterActual = codigoFuente.charAt(posActual);
				filaActual = fila;
				colActual = columna;

				return false;
			}

			return true;

		}

		// RI
		return false;
	}

	/**
	 * Metodo que me permite identificar una secuencia de caracteres como token Categoria.OPERADOR_LOGICO 
	 * @return true si la secuencia de caracteres corresponden a Categoria.OPERADOR_LOGICO
	 */
	public boolean esOperadorLogico() {
		String palabra = "";
		int posTemp = posActual;
		int fila = filaActual;
		int columna = colActual;
		if (caracterActual == 'y' || caracterActual == 'o') {

			
			palabra+= caracterActual;
			obtenerSgteCaracter();
			String charTemp = caracterActual+""; 
			if(palabra.equals(charTemp))
			{
				palabra+=caracterActual;
				obtenerSgteCaracter();
				listaTokens.add(new Token(Categoria.OPERADOR_LOGICO, palabra, fila, columna));
				return true;
			}
		}
		posActual = posTemp; 
		caracterActual = codigoFuente.charAt(posActual);
		filaActual = fila;
		colActual = columna;
		return false;
	}

	/**
	 * Metodo que me permite identificar una secuencia de caracteres como token Categoria.ENTERO 
	 * @return true si la secuencia de caracteres corresponden a Categoria.ENTERO
	 */
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

	/**
	 * Metodo que me permite identificar una secuencia de caracteres como token Categoria.IDENTIFICADOR 
	 * @return true si la secuencia de caracteres corresponden a Categoria.IDENTIFICADOR
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
	 * Metodo que me permite obtener el siguiente caracter en secuencia o que le procede al actual omitiendo saltos de linea y tabulaciones
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
	 * Metodo que me permite obtener el siguiente caracter en secuencia o que le precede al actual
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
	 * @return lista de tokens
	 */
	public ArrayList<Token> getListaTokens() {
		return listaTokens;
	}

}
