package co.sis.crirowil.persistencia;

import java.util.ArrayList;

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
			
			if(esReal()) continue;
			if(esEntero()) continue;
			if(esIdentificador()) continue;
			
			listaTokens.add(new Token(Categoria.DESCONOCIDO, ""+caracterActual, filaActual, colActual));
			obtenerSgteCaracter();
		}
		
	}
	
	public boolean esReal() {
		
		if( Character.isDigit(caracterActual) || caracterActual == '.' ) {
			String palabra = "";
			int fila = filaActual;
			int columna = colActual;
			
			//Transición
			palabra+=caracterActual;
			obtenerSgteCaracter();
			
			if(palabra.equals(".")) {
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
	
	public boolean esEntero() {
		
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

	public ArrayList<Token> getListaTokens() {
		return listaTokens;
	}

}
