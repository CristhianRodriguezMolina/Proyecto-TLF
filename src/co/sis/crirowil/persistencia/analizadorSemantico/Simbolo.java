package co.sis.crirowil.persistencia.analizadorSemantico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorSintactico.Expresion;

public class Simbolo {

	private String nombre;
	private String tipo;
	private int fila, columna;
	private Simbolo ambito;
	private Expresion expresion;
	private ArrayList<String> tipoParametros;
	
	public Simbolo(String nombre, String tipo, int fila, int columna, Simbolo ambito, Expresion expresion) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.fila = fila;
		this.columna = columna;
		this.ambito = ambito;
		this.expresion = expresion;
	}

	public Simbolo(String nombre, String tipo, Simbolo ambito, ArrayList<String> tipoParametros) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.tipoParametros = tipoParametros;
		this.ambito = ambito;
	}
	
	public Simbolo(String nombre) {
		super();
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getColumna() {
		return columna;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}

	public Simbolo getAmbito() {
		return ambito;
	}

	public void setAmbito(Simbolo ambito) {
		this.ambito = ambito;
	}

	public Expresion getExpresion() {
		return expresion;
	}

	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}

	public ArrayList<String> getTipoParametros() {
		return tipoParametros;
	}

	public void setTipoParametros(ArrayList<String> tipoParametros) {
		this.tipoParametros = tipoParametros;
	}

	@Override
	public String toString() {
		return "Simbolo [nombre=" + nombre + ", tipo=" + tipo + ", fila=" + fila + ", columna=" + columna + ", ambito="
				+ ambito + ", expresion=" + expresion + ", tipoParametros=" + tipoParametros + "]\n";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		if(tipoParametros==null) {
			result = prime * result + ((ambito == null) ? 0 : ambito.hashCode());
			result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());			
		}else {
			result = prime * result + ((ambito == null) ? 0 : ambito.hashCode());
			result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
			result = prime * result + ((tipoParametros == null) ? 0 : tipoParametros.hashCode());			
		}
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Simbolo other = (Simbolo) obj;
		
		if(tipoParametros == null) {
			
			if (ambito == null) {
				if (other.ambito != null)
					return false;
			} else if (!ambito.equals(other.ambito))
				return false;
			if (nombre == null) {
				if (other.nombre != null)
					return false;
			}else if (!nombre.equals(other.nombre))
				return false;
			
		}else {
			
			if (ambito == null) {
				if (other.ambito != null)
					return false;
			} else if (!ambito.equals(other.ambito))
				return false;
			if (nombre == null) {
				if (other.nombre != null)
					return false;
			}else if (!nombre.equals(other.nombre))
				return false;
			if (tipoParametros == null) {
				if (other.tipoParametros != null)
					return false;
			} else if (!tipoParametros.equals(other.tipoParametros))
				return false;
		}
		
		return true;
	}
	
}
