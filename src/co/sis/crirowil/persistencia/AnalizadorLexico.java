package co.sis.crirowil.persistencia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnalizadorLexico {

	private String codigoFuente;
	private ArrayList<Token> listaTokens;
	private char caracterActual, finCodigo;
	private int posActual, colActual, filaActual;
	
	public AnalizadorLexico(String codigoFuente) {
		this.codigoFuente = codigoFuente;
		this.listaTokens = new ArrayList<Token>();
		this.caracterActual = codigoFuente.charAt(posActual);
		this.finCodigo = 0;
	}
	
	public void analizar() {
		
		while(caracterActual!=finCodigo) {
			
			if(caracterActual==' ' || caracterActual=='\n' || caracterActual=='\t') {
				obtenerSgteCaracter();
				continue;
			}
			
			
			if(esOperadorLogico()) continue;
			if(esReal()) continue;
			if(esNatural()) continue;
			if(esOperadorAsignacion()) continue;
			if(esOperadorAritmetico()) continue;
			if(esComentario()) continue;
			if(esIdentificador()) continue;
			if(esCadenaCaracteres()) continue;
			
			listaTokens.add(new Token(Categoria.DESCONOCIDO, ""+caracterActual, filaActual, colActual));
			obtenerSgteCaracter();
		}
		
		Collections.sort((List<Token>) listaTokens);
		
	}
	
	public boolean esFinSentencia() {
		
		if( caracterActual=='@' ) {
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;
			
			//Transición
			palabra+=caracterActual;
			obtenerSgteCaracter();
			
			listaTokens.add(new Token(Categoria.FIN_SENTENCIA, palabra, fila, columna));
			return true;
			
		}
		
		//RI
		return false;	
	}
	
	
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
	
	
	public boolean esCadenaCaracteres() {
		
		if(caracterActual == 34)
		{
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;
			
			palabra+= caracterActual;
//			System.out.println("Empiezo: "+caracterActual);
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
				
				
//				System.out.println("Como no hay fin de cadena, regreso al caracter "+caracterActual);
				return false;
				
			}
			
			
		}
		return false;
	}
	
	public boolean esLlaves() {
		
		if( caracterActual=='{' || caracterActual=='}' ) {
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;
			
			//Transición
			palabra+=caracterActual;
			obtenerSgteCaracter();
			
			if(palabra.equals("{")) {
				listaTokens.add(new Token(Categoria.LLAVES_ABRE, palabra, fila, columna));
			}else {
				listaTokens.add(new Token(Categoria.LLAVES_CIERRA, palabra, fila, columna));
			}			
			
			return true;
			
		}
		
		//RI
		return false;	
	}
	
	public boolean esParentesis() {
		
		if( caracterActual=='(' || caracterActual==')' ) {
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;
			
			//Transición
			palabra+=caracterActual;
			obtenerSgteCaracter();
			
			if(palabra.equals("(")) {
				listaTokens.add(new Token(Categoria.PARENTESIS_ABRE, palabra, fila, columna));
			}else {
				listaTokens.add(new Token(Categoria.PARENTESIS_CIERRA, palabra, fila, columna));
			}			
			
			return true;
			
		}
		
		//RI
		return false;	
	}
	
	public boolean esTerminal() {
		
		if( caracterActual=='@' ) {
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;
			
			//Transición
			palabra+=caracterActual;
			obtenerSgteCaracter();
			
			listaTokens.add(new Token(Categoria.FIN_SENTENCIA, palabra, fila, columna));
			return true;
			
		}
		
		//RI
		return false;	
	}
	
	public boolean esSeparador() {
		
		if( caracterActual==',' ) {
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;
			
			//Transición
			palabra+=caracterActual;
			obtenerSgteCaracter();
			
			listaTokens.add(new Token(Categoria.SEPARADOR, palabra, fila, columna));
			return true;
			
		}
		
		//RI
		return false;	
	}
	
	public boolean esOperadorAsignacion() {
		
		if( caracterActual=='+' || caracterActual=='=' || caracterActual=='*' || caracterActual=='/' || caracterActual=='%' || caracterActual=='^' || caracterActual=='-' ) {
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;
			
			//Transicion
			palabra+=caracterActual;
			obtenerSgteCaracter();
			
			if(!palabra.equals("=") && palabra.equals(caracterActual+"")) {
				palabra+=caracterActual;
				obtenerSgteCaracter();	
				
				listaTokens.add(new Token(Categoria.OPERADOR_ASIGNACION, palabra, fila, columna));
				return true;
			}else if(!palabra.equals("=")) {
				obtenerAntCaracter();
				return false;
			}
			
			listaTokens.add(new Token(Categoria.OPERADOR_ASIGNACION, palabra, fila, columna));
			return true;
			
		}
		
		//RI
		return false;	
	}
	
	public boolean esOperadorAritmetico() {
		
		if( caracterActual=='+' || caracterActual=='-' || caracterActual=='*' || caracterActual=='/' || caracterActual=='%' || caracterActual=='^' ) {
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;
			
			//Transicion
			palabra+=caracterActual;
			obtenerSgteCaracter();
			
			listaTokens.add(new Token(Categoria.OPERADOR_ARITMETICO, palabra, fila, columna));
			return true;
			
		}
		
		//RI
		return false;	
	}
	
	public boolean esReal() {
		
		if( Character.isDigit(caracterActual) || caracterActual == '.' ) {
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;
			
			//Transición
			palabra+=caracterActual;
			obtenerSgteCaracter();
			
			if(palabra.equals(".") && Character.isDigit(caracterActual)) {
				while( Character.isDigit(caracterActual) ) {
					palabra+=caracterActual;
					obtenerSgteCaracter();				
				}			
			}else if(Character.isDigit(caracterActual)) {
				//Este flag verifica que ya se haya pasado una vez por un punto ('.'), ya que obivamente un real solo tiene un punto
				boolean flag = false;
				
				while( Character.isDigit(caracterActual) || caracterActual == '.' && !flag ) {
					if(caracterActual == '.') {
						flag = true;
					}
					
					palabra+=caracterActual;
					obtenerSgteCaracter();						
				}				
			}else {
				listaTokens.add(new Token(Categoria.DESCONOCIDO,palabra,fila,columna));
				return false;
			}
						
			if(palabra.contains(".") && palabra.length()>1) {
				listaTokens.add(new Token(Categoria.REAL, palabra, fila, columna));
			}else{
				listaTokens.add(new Token(Categoria.ENTERO, palabra, fila, columna));
			}			
			return true;		
		}
		
		//RI
		return false;	
	}

	
	public boolean esOperadorLogico()
	{
		if(caracterActual == 'y')
		{
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;
			
			palabra+= caracterActual;
			obtenerSgteCaracter();
			if(caracterActual == 'y')
			{
				palabra+=caracterActual;
				obtenerSgteCaracter();
				listaTokens.add(new Token(Categoria.OPERADOR_LOGICO, palabra, fila, columna));
				return true;
			}
			else
			{
				return false;
			}			
		}
		else if(caracterActual == 'o')
		{
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;
			
			palabra+= caracterActual;
			obtenerSgteCaracter();
			if(caracterActual == 'o')
			{
				palabra+=caracterActual;
				obtenerSgteCaracter();
				listaTokens.add(new Token(Categoria.OPERADOR_LOGICO, palabra, fila, columna));
				return true;
			}
			else
			{
				return false;
			}
			
		}
			
		
		
		//RI
		return false;
	}
	

	public boolean esNatural() {
		
		if( Character.isDigit(caracterActual) ) {
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;
			
			//Transición
			palabra+=caracterActual;
			obtenerSgteCaracter();
			
			while( Character.isDigit(caracterActual) ) {
				palabra+=caracterActual;
				obtenerSgteCaracter();				
			}
			
			listaTokens.add(new Token(Categoria.ENTERO, palabra, fila, columna));
			return true;
			
		}
		
		//RI
		return false;	
	}
	
	public boolean esIdentificador() {
		
		if( Character.isLetter(caracterActual) || caracterActual=='_' || caracterActual=='$' ) {
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;
			
			//Transición
			palabra+=caracterActual;
			obtenerSgteCaracter();
			
			while( Character.isLetter(caracterActual) || caracterActual=='_' || caracterActual=='$' || Character.isDigit(caracterActual) ) {
				palabra+=caracterActual;
				obtenerSgteCaracter();				
			}
			
			listaTokens.add(new Token(Categoria.IDENTIFICADOR, palabra, fila, columna));
			return true;
			
		}
		
		//RI
		return false;	
	}
	
	public void obtenerSgteCaracter() {
				
		posActual++;
		
		if(posActual<codigoFuente.length()) {
			
			if(caracterActual=='\n') {
				filaActual++;
				colActual = 0;
			}else {
				colActual++;
			}
			
			caracterActual = codigoFuente.charAt(posActual);	
		}else {
			caracterActual = finCodigo;
		}
				
		
	}
	
	public void obtenerAntCaracter() {
		
		posActual--;
		
		if(posActual > 0) {
			
			colActual--;
						
			caracterActual = codigoFuente.charAt(posActual);	
		}else {
			caracterActual = codigoFuente.charAt(0);
		}
				
		
	}

	public ArrayList<Token> getListaTokens() {
		return listaTokens;
	}

}
