package co.sis.crirowil.persistencia.analizadorSemantico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorSintactico.Argumento;
import co.sis.crirowil.persistencia.analizadorSintactico.Arreglo;
import co.sis.crirowil.persistencia.analizadorSintactico.Expresion;
import co.sis.crirowil.persistencia.analizadorSintactico.InvocacionFuncion;
import co.sis.crirowil.persistencia.analizadorSintactico.Mapa;

public class TablaSimbolos {
	
	private ArrayList<Simbolo> listaSimbolos;
	private ArrayList<String> listaErrores;
	
	public TablaSimbolos( ArrayList<String> listaErrores ) {
		this.listaSimbolos = new ArrayList<Simbolo>();
		this.listaErrores = listaErrores;
	}
	
	/**
	 * Permite guardar un símbolo de tipo variable en la tabla de símbolos 
	 */
	public Simbolo guardarSimboloVariable(String nombre, String tipo, int fila, int columna, Simbolo ambito, Expresion expresion) {
		
		Simbolo s = buscarSimboloVariable(nombre, ambito);
		
		if(s==null) {			
			Simbolo nuevo = new Simbolo(nombre, tipo, fila, columna, ambito, expresion);			
			listaSimbolos.add(nuevo);
			
			return nuevo; 
		}else {
			listaErrores.add("La variable "+nombre+" ya existe en el ámbito "+ambito);
		}
				
		return null;
	}
	
	/**
	 * Permite guardar un símbolo de tipo variable en la tabla de símbolos 
	 */
	public Simbolo guardarSimboloVariable(String nombre, String tipo, int fila, int columna, Simbolo ambito, InvocacionFuncion invocacionFuncion) {
		
		Simbolo s = buscarSimboloVariable(nombre, ambito);
		
		if(s==null) {			
			Simbolo nuevo = new Simbolo(nombre, tipo, fila, columna, ambito, invocacionFuncion);			
			listaSimbolos.add(nuevo);
			
			return nuevo; 
		}else {
			listaErrores.add("La variable "+nombre+" ya existe en el ámbito "+ambito);
		}
				
		return null;
	}
	
	/**
	 * Permite guardar un símbolo de tipo variable en la tabla de símbolos 
	 */
	public Simbolo guardarSimboloVariable(String nombre, String tipo, int fila, int columna, Simbolo ambito, Mapa mapa) {
		
		Simbolo s = buscarSimboloVariable(nombre, ambito);
		
		if(s==null) {			
			Simbolo nuevo = new Simbolo(nombre, tipo, fila, columna, ambito, mapa);			
			listaSimbolos.add(nuevo);
			
			return nuevo; 
		}else {
			listaErrores.add("La variable "+nombre+" ya existe en el ámbito "+ambito.getNombre());
		}
				
		return null;
	}
	
	/**
	 * Permite guardar un símbolo de tipo variable en la tabla de símbolos 
	 */
	public Simbolo guardarSimboloVariable(String nombre, String tipo, int fila, int columna, Simbolo ambito, Arreglo arreglo) {
		
		Simbolo s = buscarSimboloVariable(nombre, ambito);
		
		if(s==null) {			
			Simbolo nuevo = new Simbolo(nombre, tipo, fila, columna, ambito, arreglo);			
			listaSimbolos.add(nuevo);
			
			return nuevo; 
		}else {
			listaErrores.add("La variable "+nombre+" ya existe en el ámbito "+ambito.getNombre());
		}
				
		return null;
	}
	
	/**
	 * Permite guardar un símbolo de tipo variable en la tabla de símbolos 
	 */
	public Simbolo guardarSimboloVariable(String nombre, String tipo, int fila, int columna, Simbolo ambito, Argumento argumento) {
		
		Simbolo s = buscarSimboloVariable(nombre, ambito);
		
		if(s==null) {			
			Simbolo nuevo = new Simbolo(nombre, tipo, fila, columna, ambito, argumento);			
			listaSimbolos.add(nuevo);
			
			return nuevo; 
		}else {
			listaErrores.add("La variable "+nombre+" ya existe en el ámbito "+ambito.getNombre());
		}
				
		return null;
	}
	
	/**
	 * Permite guardar un símbolo de tipo función en la tabla de símbolos 
	 */
	public Simbolo guardarSimboloFuncion(String nombre, String tipo, ArrayList<String> tipoParametros) {
		
		Simbolo s = buscarSimboloFuncion(nombre, tipoParametros);
		
		if(s==null) {
			Simbolo nuevo = new Simbolo(nombre, tipo, tipoParametros);
			listaSimbolos.add(nuevo);
			
			return nuevo;
		}else {
			listaErrores.add("La función "+nombre+" ya existe");
		}		
		
		return null;
	}
	
	public Simbolo buscarSimboloVariable(String nombre, Simbolo ambito) {
		
		for (Simbolo simbolo : listaSimbolos) {
			if(simbolo.getAmbito()!=null) {
				if( nombre.equals(simbolo.getNombre()) && ambito.equals(simbolo.getAmbito()) ) {
					return simbolo;
				}
			}
		}
		
		return null;
	}
	
	public Simbolo buscarSimboloFuncion(String nombre, ArrayList<String> tiposParametros) {
		
		for (Simbolo simbolo : listaSimbolos) {
			if(simbolo.getTipoParametros()!=null) {
				if( nombre.equals(simbolo.getNombre()) && tiposParametros.equals(simbolo.getTipoParametros()) ) {
					return simbolo;
				}
			}
		}
		
		return null;
	}

	public ArrayList<Simbolo> getListaSimbolos() {
		return listaSimbolos;
	}

	public void setListaSimbolos(ArrayList<Simbolo> listaSimbolos) {
		this.listaSimbolos = listaSimbolos;
	}

}
