package co.sis.crirowil.persistencia.analizadorSintactico;

/**
 * Me permite guardar errores sintanticos presentes en el codigo fuente
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class ErrorSintactico {
	
	/**
	 * Guarda la fila del error sintactico
	 */
	private int fila;
	
	/**
	 * Guarda la columna del error sintactico 
	 */
	private int columna;
	
	/*
	 * Guarda el mensaje del error
	 */
	private String mensaje;
	
	/**
	 * Constructor del Error Sintactico
	 * @param fila
	 * @param columna
	 * @param token
	 */
	public ErrorSintactico(String mensaje, int fila, int columna) {
		super();
		this.fila = fila;
		this.columna = columna;
		this.mensaje = mensaje;
	}
	/**
	 * @return the fila
	 */
	public int getFila() {
		return fila;
	}
	/**
	 * @param fila the fila to set
	 */
	public void setFila(int fila) {
		this.fila = fila;
	}
	/**
	 * @return the columna
	 */
	public int getColumna() {
		return columna;
	}
	/**
	 * @param columna the columna to set
	 */
	public void setColumna(int columna) {
		this.columna = columna;
	}
}
