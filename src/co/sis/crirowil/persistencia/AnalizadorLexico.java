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
			
			if(esEntero()) continue;
			if(esIdentificador()) continue;
			
			listaTokens.add(new Token(Categoria.DESCONOCIDO, ""+caracterActual, filaActual, colActual));
			obtenerSgteCaracter();
		}
		
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
